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
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.items" size="mini">
            <el-table-column label="物料编码" prop="materialCode" align="center" width="120" />
            <el-table-column label="物料名称" prop="materialName" align="center" min-width="180" />
            <el-table-column label="规格型号" prop="specification" align="center" min-width="160" />
            <el-table-column label="单位" prop="unit" align="center" width="70" />
            <el-table-column label="账面数量" prop="bookQty" align="center" width="90" />
            <el-table-column label="实盘数量" prop="checkQty" align="center" width="90" />
            <el-table-column label="盈亏数量" align="center" width="90">
              <template slot-scope="item">
                <el-tag v-if="item.row.diffQty > 0" type="success" size="mini">+{{ item.row.diffQty }}</el-tag>
                <el-tag v-else-if="item.row.diffQty < 0" type="danger" size="mini">{{ item.row.diffQty }}</el-tag>
                <span v-else>0</span>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="盘点单号" align="center" prop="checkNo" width="170" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="盘点日期" align="center" prop="checkDate" width="120" />
      <el-table-column label="盘点人" align="center" prop="checkUser" width="90" />
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
            icon="el-icon-promotion"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:stockCheck:edit']"
          >重新提交</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-select v-model="form.warehouseName" placeholder="请选择仓库" style="width: 100%">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="盘点日期" prop="checkDate">
              <el-date-picker v-model="form.checkDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="盘点人" prop="checkUser">
              <el-input v-model="form.checkUser" placeholder="请输入盘点人" />
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="left">盘点明细</el-divider>
        <el-table :data="form.items" border size="mini">
          <el-table-column label="物料" min-width="180" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.materialId" placeholder="请选择物料" filterable style="width: 100%" @change="materialChange(scope.row)">
                <el-option v-for="m in materialOptions" :key="m.materialId" :label="m.materialName" :value="m.materialId" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="规格型号" prop="specification" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.specification }}</span>
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="70" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.unit }}</span>
            </template>
          </el-table-column>
          <el-table-column label="账面数量" min-width="100" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.bookQty" :min="0" :controls="false" size="mini" style="width: 100%" @change="lineCalc(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="实盘数量" min-width="100" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.checkQty" :min="0" :controls="false" size="mini" style="width: 100%" @change="lineCalc(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="盈亏数量" prop="diffQty" width="100" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.diffQty > 0" type="success" size="mini">+{{ scope.row.diffQty }}</el-tag>
              <el-tag v-else-if="scope.row.diffQty < 0" type="danger" size="mini">{{ scope.row.diffQty }}</el-tag>
              <span v-else>0</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="70" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="removeLine(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; text-align: right">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addLine">添加盘点行</el-button>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStockCheck, getStockCheck, delStockCheck, addStockCheck, updateStockCheck, submitStockCheck, approveStockCheck, rejectStockCheck, completeStockCheck } from "@/api/erp/stock"

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
      // 盘点状态字典（统一单据流程：草稿-待审核-审核通过/驳回-完成）
      checkStatusOptions: [
        { value: '0', label: '草稿', tagType: 'info' },
        { value: '1', label: '待审核', tagType: 'warning' },
        { value: '2', label: '审核通过', tagType: 'primary' },
        { value: '3', label: '已驳回', tagType: 'danger' },
        { value: '4', label: '已完成', tagType: 'success' }
      ],
      // 仓库选项（mock）
      warehouseOptions: [
        { warehouseId: 1, warehouseName: '总仓-原材料仓' },
        { warehouseId: 2, warehouseName: '半成品仓' },
        { warehouseId: 3, warehouseName: '成品仓' },
        { warehouseId: 4, warehouseName: '华东周转仓' }
      ],
      // 物料选项（mock）
      materialOptions: [
        { materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨' },
        { materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个' },
        { materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台' },
        { materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台' },
        { materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台' },
        { materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', specification: '60*40*40cm', unit: '个' }
      ],
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
        checkDate: [
          { required: true, message: "盘点日期不能为空", trigger: "change" }
        ],
        checkUser: [
          { required: true, message: "盘点人不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "盘点状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
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
        checkDate: undefined,
        checkUser: undefined,
        status: "0",
        remark: undefined,
        items: []
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
    /** 提交审核 */
    handleSubmit(row) {
      this.$modal.confirm('确认提交单据「' + row.checkNo + '」审核？').then(function() {
        return submitStockCheck(row.checkId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过单据「' + row.checkNo + '」？').then(function() {
        return approveStockCheck(row.checkId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回单据「' + row.checkNo + '」？').then(function() {
        return rejectStockCheck(row.checkId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成单据「' + row.checkNo + '」？').then(function() {
        return completeStockCheck(row.checkId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已完成")
      }).catch(() => {})
    },
    /** 选择物料回填行数据 */
    materialChange(row) {
      const m = this.materialOptions.find(item => item.materialId === row.materialId)
      if (m) {
        row.materialCode = m.materialCode
        row.materialName = m.materialName
        row.specification = m.specification
        row.unit = m.unit
        row.bookQty = row.bookQty || undefined
        this.lineCalc(row)
      }
    },
    /** 计算盈亏数量 */
    lineCalc(row) {
      row.diffQty = Math.round((Number(row.checkQty || 0) - Number(row.bookQty || 0)) * 100) / 100
    },
    /** 添加盘点行 */
    addLine() {
      this.form.items.push({
        itemId: undefined, materialId: undefined, materialCode: undefined, materialName: undefined,
        specification: undefined, unit: undefined, bookQty: undefined, checkQty: undefined, diffQty: undefined
      })
    },
    /** 删除盘点行 */
    removeLine(index) {
      this.form.items.splice(index, 1)
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.items || this.form.items.length === 0) {
            this.$modal.msgWarning("请添加盘点行")
            return
          }
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