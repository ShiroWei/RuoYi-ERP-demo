<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="调拨单号" prop="transferNo">
        <el-input
          v-model="queryParams.transferNo"
          placeholder="请输入调拨单号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['erp:stockTransfer:add']"
        >新增调拨单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList">
      <el-table-column label="调拨单号" align="center" prop="transferNo" width="170" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="110" />
      <el-table-column label="物料名称" align="center" prop="materialName" :show-overflow-tooltip="true" />
      <el-table-column label="调拨数量" align="center" prop="quantity" width="100" />
      <el-table-column label="调出仓库" align="center" prop="fromWarehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="调入仓库" align="center" prop="toWarehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="调拨日期" align="center" prop="transferDate" width="120" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="billStatusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-promotion"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:stockTransfer:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:stockTransfer:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:stockTransfer:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:stockTransfer:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:stockTransfer:edit']"
          >重新提交</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增调拨单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="物料" prop="materialId">
          <el-select v-model="form.materialId" placeholder="请选择物料" filterable style="width: 100%" @change="materialChange">
            <el-option v-for="m in materialOptions" :key="m.materialId" :label="m.materialName + '（' + m.materialCode + '）'" :value="m.materialId" />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="调出仓库" prop="fromWarehouseName">
              <el-select v-model="form.fromWarehouseName" placeholder="请选择仓库" style="width: 100%" @change="fromWarehouseChange">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调入仓库" prop="toWarehouseName">
              <el-select v-model="form.toWarehouseName" placeholder="请选择仓库" style="width: 100%" @change="toWarehouseChange">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="调拨数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="0" :controls="false" style="width: 100%" placeholder="调拨数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调拨日期" prop="transferDate">
              <el-date-picker v-model="form.transferDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="单据状态" style="width: 100%">
            <el-option v-for="dict in billStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
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
import { listStockTransfer, addStockTransfer, submitStockTransfer, approveStockTransfer, rejectStockTransfer, completeStockTransfer } from "@/api/erp/stock"
import { listWarehouse, listMaterial } from "@/api/erp/base"

export default {
  name: "StockTransfer",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调拨单表格数据
      transferList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
        transferNo: undefined,
        materialName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialId: [
          { required: true, message: "物料不能为空", trigger: "change" }
        ],
        fromWarehouseName: [
          { required: true, message: "调出仓库不能为空", trigger: "change" }
        ],
        toWarehouseName: [
          { required: true, message: "调入仓库不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "调拨数量不能为空", trigger: "blur" }
        ],
        transferDate: [
          { required: true, message: "调拨日期不能为空", trigger: "change" }
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
    /** 选择调出仓库回填 id */
    fromWarehouseChange() {
      const w = this.warehouseOptions.find(item => item.warehouseName === this.form.fromWarehouseName)
      this.form.fromWarehouseId = w ? w.warehouseId : undefined
    },
    /** 选择调入仓库回填 id */
    toWarehouseChange() {
      const w = this.warehouseOptions.find(item => item.warehouseName === this.form.toWarehouseName)
      this.form.toWarehouseId = w ? w.warehouseId : undefined
    },
    /** 查询调拨单列表 */
    getList() {
      this.loading = true
      listStockTransfer(this.queryParams).then(response => {
        this.transferList = response.rows
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
        transferId: undefined,
        transferNo: undefined,
        materialId: undefined,
        materialCode: undefined,
        materialName: undefined,
        quantity: undefined,
        fromWarehouseName: undefined,
        fromWarehouseId: undefined,
        toWarehouseName: undefined,
        toWarehouseId: undefined,
        transferDate: undefined,
        status: "0"
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
    /** 提交审核 */
    handleSubmit(row) {
      this.$modal.confirm('确认提交单据「' + row.transferNo + '」审核？').then(function() {
        return submitStockTransfer(row.transferId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过单据「' + row.transferNo + '」？').then(function() {
        return approveStockTransfer(row.transferId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回单据「' + row.transferNo + '」？').then(function() {
        return rejectStockTransfer(row.transferId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成单据「' + row.transferNo + '」？').then(function() {
        return completeStockTransfer(row.transferId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已完成")
      }).catch(() => {})
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增调拨单"
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
          if (this.form.fromWarehouseName === this.form.toWarehouseName) {
            this.$modal.msgWarning("调出仓库与调入仓库不能相同")
            return
          }
          addStockTransfer(this.form).then(() => {
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