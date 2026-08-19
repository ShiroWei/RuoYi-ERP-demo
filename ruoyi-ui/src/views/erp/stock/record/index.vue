<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="记录单号" prop="recordNo">
        <el-input
          v-model="queryParams.recordNo"
          placeholder="请输入记录单号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出入库类型" prop="recordType">
        <el-select v-model="queryParams.recordType" placeholder="出入库类型" clearable style="width: 160px">
          <el-option v-for="dict in recordTypeOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-select v-model="queryParams.warehouseName" placeholder="仓库名称" clearable style="width: 180px">
          <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:stockRecord:add']"
        >新增记录</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList">
      <el-table-column label="记录单号" align="center" prop="recordNo" width="170" />
      <el-table-column label="出入库类型" align="center" prop="recordType" width="110">
        <template slot-scope="scope">
          <dict-tag :options="recordTypeOptions" :value="scope.row.recordType"/>
        </template>
      </el-table-column>
      <el-table-column label="物料编码" align="center" prop="materialCode" width="110" />
      <el-table-column label="物料名称" align="center" prop="materialName" :show-overflow-tooltip="true" />
      <el-table-column label="数量" align="center" prop="quantity" width="100" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="关联单据" align="center" prop="bizNo" width="170" />
      <el-table-column label="经办人" align="center" prop="operator" width="90" />
      <el-table-column label="出入库日期" align="center" prop="recordDate" width="120" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加入出库记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="出入库类型" prop="recordType">
              <el-select v-model="form.recordType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="dict in recordTypeOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-select v-model="form.warehouseName" placeholder="请选择仓库" style="width: 100%" @change="warehouseChange">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="物料" prop="materialId">
          <el-select v-model="form.materialId" placeholder="请选择物料" filterable style="width: 100%" @change="materialChange">
            <el-option v-for="m in materialOptions" :key="m.materialId" :label="m.materialName + '（' + m.materialCode + '）'" :value="m.materialId" />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="0" :controls="false" style="width: 100%" placeholder="数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联单据" prop="bizNo">
              <el-input v-model="form.bizNo" placeholder="请输入关联单据号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经办人" prop="operator">
              <el-input v-model="form.operator" placeholder="请输入经办人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出入库日期" prop="recordDate">
              <el-date-picker v-model="form.recordDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStockRecord, addStockRecord } from "@/api/erp/stock"
import { listWarehouse, listMaterial } from "@/api/erp/base"

export default {
  name: "StockRecord",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 出入库类型字典（接入真实接口后使用 sys_dict 的 erp_stock_record_type）
      recordTypeOptions: [
        { value: '1', label: '采购入库', tagType: 'success' },
        { value: '2', label: '销售出库', tagType: 'danger' },
        { value: '3', label: '生产入库', tagType: 'success' },
        { value: '4', label: '生产领料', tagType: 'danger' },
        { value: '5', label: '盘盈入库', tagType: 'success' },
        { value: '6', label: '盘亏出库', tagType: 'danger' },
        { value: '7', label: '调拨入库', tagType: 'primary' },
        { value: '8', label: '调拨出库', tagType: 'warning' }
      ],
      // 单据状态字典（接入真实接口后使用 sys_dict 的 erp_bill_status）
      billStatusOptions: [
        { value: '0', label: '草稿', tagType: 'info' },
        { value: '1', label: '待审核', tagType: 'warning' },
        { value: '2', label: '审核通过', tagType: 'primary' },
        { value: '3', label: '已驳回', tagType: 'danger' },
        { value: '4', label: '已完成', tagType: 'success' }
      ],
      // 仓库选项（接入真实接口后动态加载）
      warehouseOptions: [],
      // 物料选项（接入真实接口后动态加载）
      materialOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordNo: undefined,
        recordType: undefined,
        warehouseName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recordType: [
          { required: true, message: "出入库类型不能为空", trigger: "change" }
        ],
        warehouseName: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ],
        materialId: [
          { required: true, message: "物料不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
        recordDate: [
          { required: true, message: "出入库日期不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadWarehouse()
    this.loadMaterial()
  },
  methods: {
    /** 加载仓库下拉 */
    loadWarehouse() {
      listWarehouse({ pageNum: 1, pageSize: 100 }).then(response => {
        this.warehouseOptions = response.rows
      })
    },
    /** 加载物料下拉 */
    loadMaterial() {
      listMaterial({ pageNum: 1, pageSize: 100 }).then(response => {
        this.materialOptions = response.rows
      })
    },
    /** 选择仓库回填 id */
    warehouseChange() {
      const w = this.warehouseOptions.find(item => item.warehouseName === this.form.warehouseName)
      this.form.warehouseId = w ? w.warehouseId : undefined
    },
    /** 查询记录列表 */
    getList() {
      this.loading = true
      listStockRecord(this.queryParams).then(response => {
        this.recordList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        recordId: undefined,
        recordNo: undefined,
        recordType: undefined,
        materialId: undefined,
        materialCode: undefined,
        materialName: undefined,
        quantity: undefined,
        warehouseName: undefined,
        warehouseId: undefined,
        bizNo: undefined,
        operator: undefined,
        recordDate: undefined,
        remark: undefined
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增出入库记录"
    },
    /** 选择物料回填行数据 */
    materialChange(materialId) {
      const m = this.materialOptions.find(item => item.materialId === materialId)
      if (m) {
        this.form.materialCode = m.materialCode
        this.form.materialName = m.materialName
      }
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addStockRecord(this.form).then(() => {
            this.$modal.msgSuccess("新增成功")
            this.open = false
            this.getList()
          })
        }
      })
    }
  }
}
</script>