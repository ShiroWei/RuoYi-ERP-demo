<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="工单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入工单编号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="生产状态" clearable style="width: 160px">
          <el-option v-for="dict in productionStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          v-hasPermi="['erp:workOrder:add']"
        >新增工单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:workOrder:edit']"
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
          v-hasPermi="['erp:workOrder:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.items" size="mini">
            <el-table-column label="物料编码" prop="materialCode" align="center" width="120" />
            <el-table-column label="物料名称" prop="materialName" align="center" min-width="180" />
            <el-table-column label="规格型号" prop="specification" align="center" min-width="180" />
            <el-table-column label="单位" prop="unit" align="center" width="70" />
            <el-table-column label="领料数量" prop="quantity" align="center" width="110" />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="工单编号" align="center" prop="orderNo" width="170" />
      <el-table-column label="产品编码" align="center" prop="productCode" width="110" />
      <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true" />
      <el-table-column label="规格型号" align="center" prop="productSpec" :show-overflow-tooltip="true" />
      <el-table-column label="生产数量" align="center" prop="quantity" width="90" />
      <el-table-column label="计划开工" align="center" prop="planStart" width="110" />
      <el-table-column label="计划完工" align="center" prop="planEnd" width="110" />
      <el-table-column label="优先级" align="center" prop="priority" width="80">
        <template slot-scope="scope">
          <dict-tag :options="priorityOptions" :value="scope.row.priority"/>
        </template>
      </el-table-column>
      <el-table-column label="生产状态" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <dict-tag :options="productionStatusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="车间" align="center" prop="workshop" width="90" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-promotion"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >重新提交</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:workOrder:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:workOrder:remove']"
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

    <!-- 添加或修改生产工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产产品" prop="productId">
              <el-select v-model="form.productId" placeholder="请选择产品" filterable style="width: 100%" @change="productChange">
                <el-option v-for="m in productOptions" :key="m.materialId" :label="m.materialName" :value="m.materialId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="0" :controls="false" style="width: 100%" placeholder="生产数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划开工" prop="planStart">
              <el-date-picker v-model="form.planStart" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划完工" prop="planEnd">
              <el-date-picker v-model="form.planEnd" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="优先级" prop="priority">
              <el-select v-model="form.priority" placeholder="优先级" style="width: 100%">
                <el-option v-for="dict in priorityOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产状态" prop="status">
              <el-select v-model="form.status" placeholder="生产状态" style="width: 100%">
                <el-option v-for="dict in productionStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产车间" prop="workshop">
              <el-select v-model="form.workshop" placeholder="车间" style="width: 100%">
                <el-option label="一车间" value="一车间" />
                <el-option label="二车间" value="二车间" />
                <el-option label="三车间" value="三车间" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="left">领料清单（按BOM自动生成）</el-divider>
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
          <el-table-column label="领料数量" min-width="110" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="0" :controls="false" size="mini" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="70" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="removeLine(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; text-align: right">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addLine">添加领料行</el-button>
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
import { listWorkOrder, getWorkOrder, delWorkOrder, addWorkOrder, updateWorkOrder, submitWorkOrder, approveWorkOrder, rejectWorkOrder, completeWorkOrder } from "@/api/erp/production"

export default {
  name: "ProductionOrder",
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
      // 生产工单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 工单状态字典（统一单据流程：草稿-待审核-审核通过/驳回-完成）
      productionStatusOptions: [
        { value: '0', label: '草稿', tagType: 'info' },
        { value: '1', label: '待审核', tagType: 'warning' },
        { value: '2', label: '审核通过', tagType: 'primary' },
        { value: '3', label: '已驳回', tagType: 'danger' },
        { value: '4', label: '已完成', tagType: 'success' }
      ],
      // 优先级字典（接入真实接口后使用 sys_dict 的 erp_priority）
      priorityOptions: [
        { value: '1', label: '高', tagType: 'danger' },
        { value: '2', label: '中', tagType: 'warning' },
        { value: '3', label: '低', tagType: 'info' }
      ],
      // 产品选项（mock）
      productOptions: [
        { materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台' },
        { materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台' },
        { materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台' }
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
        orderNo: undefined,
        productName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "生产产品不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "生产数量不能为空", trigger: "blur" }
        ],
        planStart: [
          { required: true, message: "计划开工不能为空", trigger: "change" }
        ],
        planEnd: [
          { required: true, message: "计划完工不能为空", trigger: "change" }
        ],
        priority: [
          { required: true, message: "优先级不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "生产状态不能为空", trigger: "change" }
        ],
        workshop: [
          { required: true, message: "生产车间不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询生产工单列表 */
    getList() {
      this.loading = true
      listWorkOrder(this.queryParams).then(response => {
        this.orderList = response.rows
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
        orderId: undefined,
        orderNo: undefined,
        productId: undefined,
        productCode: undefined,
        productName: undefined,
        productSpec: undefined,
        unit: undefined,
        quantity: undefined,
        planStart: undefined,
        planEnd: undefined,
        priority: '2',
        status: '0',
        workshop: undefined,
        bomCode: undefined,
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
    /** 提交审核 */
    handleSubmit(row) {
      this.$modal.confirm('确认提交工单「' + row.orderNo + '」审核？').then(function() {
        return submitWorkOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过工单「' + row.orderNo + '」？').then(function() {
        return approveWorkOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回工单「' + row.orderNo + '」？').then(function() {
        return rejectWorkOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成工单「' + row.orderNo + '」？').then(function() {
        return completeWorkOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已完成")
      }).catch(() => {})
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
      this.title = "新增生产工单"
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getWorkOrder(orderId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改生产工单"
      })
    },
    /** 选择产品回填数据 */
    productChange(productId) {
      const m = this.productOptions.find(item => item.materialId === productId)
      if (m) {
        this.form.productCode = m.materialCode
        this.form.productName = m.materialName
        this.form.productSpec = m.specification
        this.form.unit = m.unit
      }
    },
    /** 选择物料回填行数据 */
    materialChange(row) {
      const m = this.materialOptions.find(item => item.materialId === row.materialId)
      if (m) {
        row.materialCode = m.materialCode
        row.materialName = m.materialName
        row.specification = m.specification
        row.unit = m.unit
      }
    },
    /** 添加领料行 */
    addLine() {
      this.form.items.push({
        itemId: undefined, materialId: undefined, materialCode: undefined, materialName: undefined,
        specification: undefined, unit: undefined, quantity: undefined
      })
    },
    /** 删除领料行 */
    removeLine(index) {
      this.form.items.splice(index, 1)
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planEnd < this.form.planStart) {
            this.$modal.msgWarning("计划完工日期不能早于计划开工日期")
            return
          }
          if (this.form.orderId != undefined) {
            updateWorkOrder(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWorkOrder(this.form).then(() => {
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
      const orderIds = row.orderId || this.ids
      this.$modal.confirm('是否确认删除生产工单编号为"' + orderIds + '"的数据项？').then(function() {
        return delWorkOrder(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>