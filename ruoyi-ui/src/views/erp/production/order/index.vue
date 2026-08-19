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
      <el-table-column label="工单编号" align="center" prop="orderNo" width="170" />
      <el-table-column label="产品编码" align="center" prop="productCode" width="110" />
      <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true" />
      <el-table-column label="计划数量" align="center" prop="planQty" width="90" />
      <el-table-column label="完工数量" align="center" prop="finishQty" width="90" />
      <el-table-column label="工单日期" align="center" prop="orderDate" width="110" />
      <el-table-column label="计划开工" align="center" prop="planStartDate" width="110" />
      <el-table-column label="计划完工" align="center" prop="planEndDate" width="110" />
      <el-table-column label="生产状态" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <dict-tag :options="productionStatusOptions" :value="scope.row.status"/>
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
    <el-dialog :title="title" :visible.sync="open" width="640px" append-to-body>
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
            <el-form-item label="计划数量" prop="planQty">
              <el-input-number v-model="form.planQty" :min="0" :controls="false" style="width: 100%" placeholder="计划数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工单日期" prop="orderDate">
              <el-date-picker v-model="form.orderDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产状态" prop="status">
              <el-select v-model="form.status" placeholder="生产状态" style="width: 100%">
                <el-option v-for="dict in productionStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划开工" prop="planStartDate">
              <el-date-picker v-model="form.planStartDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划完工" prop="planEndDate">
              <el-date-picker v-model="form.planEndDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
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
import { listWorkOrder, getWorkOrder, delWorkOrder, addWorkOrder, updateWorkOrder } from "@/api/erp/production"
import { listMaterial } from "@/api/erp/base"

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
      // 工单状态字典（0未开始 1生产中 2已完工 3已关闭）
      productionStatusOptions: [
        { value: '0', label: '未开始', tagType: 'info' },
        { value: '1', label: '生产中', tagType: 'warning' },
        { value: '2', label: '已完工', tagType: 'success' },
        { value: '3', label: '已关闭', tagType: 'danger' }
      ],
      // 产品选项（接入真实接口后动态加载）
      productOptions: [],
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
        planQty: [
          { required: true, message: "计划数量不能为空", trigger: "blur" }
        ],
        orderDate: [
          { required: true, message: "工单日期不能为空", trigger: "change" }
        ],
        planStartDate: [
          { required: true, message: "计划开工不能为空", trigger: "change" }
        ],
        planEndDate: [
          { required: true, message: "计划完工不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadMaterial()
  },
  methods: {
    /** 加载产品下拉 */
    loadMaterial() {
      listMaterial({ pageNum: 1, pageSize: 100 }).then(response => {
        this.productOptions = response.rows
      })
    },
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
        planQty: undefined,
        finishQty: undefined,
        orderDate: undefined,
        planStartDate: undefined,
        planEndDate: undefined,
        status: '0',
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
      }
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planEndDate < this.form.planStartDate) {
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