<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="盘点单号" prop="checkNo">
        <el-input
          v-model="queryParams.checkNo"
          placeholder="请输入盘点单号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-select v-model="queryParams.warehouseName" placeholder="仓库名称" clearable style="width: 180px">
          <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
        </el-select>
      </el-form-item>
      <el-form-item label="盘点状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="盘点状态" clearable style="width: 160px">
          <el-option v-for="dict in checkStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          v-hasPermi="['erp:stockCheck:add']"
        >新增盘点单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:stockCheck:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:stockCheck:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="盘点单号" align="center" prop="checkNo" width="170" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="110" />
      <el-table-column label="物料名称" align="center" prop="materialName" :show-overflow-tooltip="true" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="账面数量" align="center" prop="bookQty" width="90" />
      <el-table-column label="实盘数量" align="center" prop="actualQty" width="90" />
      <el-table-column label="盈亏数量" align="center" width="90">
        <template slot-scope="item">
          <el-tag v-if="item.row.diffQty > 0" type="success" size="mini">+{{ item.row.diffQty }}</el-tag>
          <el-tag v-else-if="item.row.diffQty < 0" type="danger" size="mini">{{ item.row.diffQty }}</el-tag>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点日期" align="center" prop="checkDate" width="120" />
      <el-table-column label="盘点状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="checkStatusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:stockCheck:remove']"
          >删除</el-button>
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

    <!-- 添加或修改盘点单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="640px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-select v-model="form.warehouseName" placeholder="请选择仓库" style="width: 100%" @change="warehouseChange">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料" prop="materialId">
              <el-select v-model="form.materialId" placeholder="请选择物料" filterable style="width: 100%" @change="materialChange">
                <el-option v-for="m in materialOptions" :key="m.materialId" :label="m.materialName + '（' + m.materialCode + '）'" :value="m.materialId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="盘点日期" prop="checkDate">
              <el-date-picker v-model="form.checkDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点状态" prop="status">
              <el-select v-model="form.status" placeholder="盘点状态" style="width: 100%">
                <el-option v-for="dict in checkStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="账面数量" prop="bookQty">
              <el-input-number v-model="form.bookQty" :min="0" :controls="false" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实盘数量" prop="actualQty">
              <el-input-number v-model="form.actualQty" :min="0" :controls="false" style="width: 100%" @change="calcDiff" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盈亏数量">
              <el-tag :type="form.diffQty > 0 ? 'success' : form.diffQty < 0 ? 'danger' : 'info'" size="medium">{{ form.diffQty }}</el-tag>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStockCheck, getStockCheck, delStockCheck, addStockCheck, updateStockCheck } from "@/api/erp/stock"
import { listWarehouse, listMaterial } from "@/api/erp/base"

export default {
  name: "StockCheck",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 盘点单表格数据
      checkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 盘点状态字典（0未盘点 1已盘点）
      checkStatusOptions: [
        { value: '0', label: '未盘点', tagType: 'info' },
        { value: '1', label: '已盘点', tagType: 'success' }
      ],
      // 仓库选项（接入真实接口后动态加载）
      warehouseOptions: [],
      // 物料选项（接入真实接口后动态加载）
      materialOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        checkNo: undefined,
        warehouseName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        warehouseName: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ],
        materialId: [
          { required: true, message: "物料不能为空", trigger: "change" }
        ],
        checkDate: [
          { required: true, message: "盘点日期不能为空", trigger: "change" }
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
    /** 选择物料回填信息 */
    materialChange(materialId) {
      const m = this.materialOptions.find(item => item.materialId === materialId)
      if (m) {
        this.form.materialCode = m.materialCode
        this.form.materialName = m.materialName
      }
    },
    /** 计算盈亏数量 */
    calcDiff() {
      this.form.diffQty = Math.round((Number(this.form.actualQty || 0) - Number(this.form.bookQty || 0)) * 100) / 100
    },
    /** 查询盘点单列表 */
    getList() {
      this.loading = true
      listStockCheck(this.queryParams).then(response => {
        this.checkList = response.rows
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
        checkId: undefined,
        checkNo: undefined,
        warehouseName: undefined,
        warehouseId: undefined,
        materialId: undefined,
        materialCode: undefined,
        materialName: undefined,
        bookQty: undefined,
        actualQty: undefined,
        diffQty: 0,
        checkDate: undefined,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增盘点单"
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.checkId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const checkId = row.checkId || this.ids
      getStockCheck(checkId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改盘点单"
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.calcDiff()
          if (this.form.checkId != undefined) {
            updateStockCheck(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStockCheck(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const checkIds = row.checkId || this.ids
      this.$modal.confirm('是否确认删除盘点单编号为"' + checkIds + '"的数据项？').then(function() {
        return delStockCheck(checkIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>