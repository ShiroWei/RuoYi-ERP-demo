<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="单据状态" clearable style="width: 160px">
          <el-option v-for="dict in billStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          v-hasPermi="['erp:saleOrder:add']"
        >新增订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:saleOrder:edit']"
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
          v-hasPermi="['erp:saleOrder:remove']"
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
            <el-table-column label="数量" prop="quantity" align="center" width="100" />
            <el-table-column label="单价(元)" prop="price" align="center" width="100" />
            <el-table-column label="金额(元)" prop="amount" align="center" width="120" />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="订单编号" align="center" prop="orderNo" width="160" />
      <el-table-column label="客户名称" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column label="订单日期" align="center" prop="orderDate" width="120" />
      <el-table-column label="订单金额(元)" align="center" prop="totalAmount" width="130" />
      <el-table-column label="单据状态" align="center" prop="status" width="100">
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
            v-hasPermi="['erp:saleOrder:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:saleOrder:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:saleOrder:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:saleOrder:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:saleOrder:edit']"
          >重新提交</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['erp:saleOrder:query']"
          >明细</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:saleOrder:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:saleOrder:remove']"
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

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户" prop="customerName">
              <el-select v-model="form.customerName" placeholder="请选择客户" filterable style="width: 100%" @change="customerChange">
                <el-option v-for="item in customerOptions" :key="item.customerId" :label="item.customerName" :value="item.customerName" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单日期" prop="orderDate">
              <el-date-picker v-model="form.orderDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单据状态" prop="status">
              <el-select v-model="form.status" placeholder="单据状态" style="width: 100%">
                <el-option v-for="dict in billStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="left">销售明细</el-divider>
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
          <el-table-column label="数量" min-width="100" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="0" :controls="false" size="mini" style="width: 100%" @change="lineCalc(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="单价(元)" min-width="100" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.price" :min="0" :precision="2" :controls="false" size="mini" style="width: 100%" @change="lineCalc(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="金额(元)" prop="amount" width="110" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.amount }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="70" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="removeLine(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; text-align: right">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addLine">添加明细行</el-button>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <span class="el-form-item__label" style="margin-right: 16px">订单金额：{{ form.totalAmount }} 元</span>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 销售订单明细查看对话框 -->
    <el-dialog title="销售订单明细" :visible.sync="openDetail" width="760px" append-to-body>
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="订单编号">{{ detail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="客户">{{ detail.customerName }}</el-descriptions-item>
        <el-descriptions-item label="订单日期">{{ detail.orderDate }}</el-descriptions-item>
        <el-descriptions-item label="单据状态">
          <dict-tag :options="billStatusOptions" :value="detail.status"/>
        </el-descriptions-item>
        <el-descriptions-item label="订单金额">{{ detail.totalAmount }} 元</el-descriptions-item>
        <el-descriptions-item label="备注">{{ detail.remark }}</el-descriptions-item>
      </el-descriptions>
      <el-table :data="detail.items" border size="mini" style="margin-top: 12px">
        <el-table-column label="物料编码" prop="materialCode" align="center" width="120" />
        <el-table-column label="物料名称" prop="materialName" align="center" min-width="180" />
        <el-table-column label="规格型号" prop="specification" align="center" min-width="160" />
        <el-table-column label="单位" prop="unit" align="center" width="70" />
        <el-table-column label="数量" prop="quantity" align="center" width="90" />
        <el-table-column label="单价(元)" prop="price" align="center" width="100" />
        <el-table-column label="金额(元)" prop="amount" align="center" width="110" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listSaleOrder, getSaleOrder, delSaleOrder, addSaleOrder, updateSaleOrder, submitSaleOrder, approveSaleOrder, rejectSaleOrder, completeSaleOrder } from "@/api/erp/sale"
import { listCustomer, listMaterial } from "@/api/erp/base"

export default {
  name: "SaleOrder",
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
      // 销售订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示明细
      openDetail: false,
      // 明细数据
      detail: {},
      // 单据状态字典（接入真实接口后使用 sys_dict 的 erp_bill_status）
      billStatusOptions: [
        { value: '0', label: '草稿', tagType: 'info' },
        { value: '1', label: '待审核', tagType: 'warning' },
        { value: '2', label: '审核通过', tagType: 'primary' },
        { value: '3', label: '已驳回', tagType: 'danger' },
        { value: '4', label: '已完成', tagType: 'success' }
      ],
      // 客户选项（接入真实接口后动态加载）
      customerOptions: [],
      // 物料选项（接入真实接口后动态加载）
      materialOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: undefined,
        customerName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerName: [
          { required: true, message: "客户不能为空", trigger: "change" }
        ],
        orderDate: [
          { required: true, message: "订单日期不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadCustomer()
    this.loadMaterial()
  },
  methods: {
    /** 加载客户下拉 */
    loadCustomer() {
      listCustomer({ pageNum: 1, pageSize: 100 }).then(response => {
        this.customerOptions = response.rows
      })
    },
    /** 加载物料下拉 */
    loadMaterial() {
      listMaterial({ pageNum: 1, pageSize: 100 }).then(response => {
        this.materialOptions = response.rows
      })
    },
    /** 查询销售订单列表 */
    getList() {
      this.loading = true
      listSaleOrder(this.queryParams).then(response => {
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
        customerId: undefined,
        customerName: undefined,
        orderDate: undefined,
        status: "0",
        remark: undefined,
        totalAmount: 0,
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
      this.title = "新增销售订单"
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
      getSaleOrder(orderId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改销售订单"
      })
    },
    /** 明细按钮操作 */
    handleDetail(row) {
      this.detail = row
      this.openDetail = true
    },
    /** 提交审核 */
    handleSubmit(row) {
      this.$modal.confirm('确认提交单据「' + row.orderNo + '」审核？').then(function() {
        return submitSaleOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过单据「' + row.orderNo + '」？').then(function() {
        return approveSaleOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回单据「' + row.orderNo + '」？').then(function() {
        return rejectSaleOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成单据「' + row.orderNo + '」？').then(function() {
        return completeSaleOrder(row.orderId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已完成")
      }).catch(() => {})
    },
    /** 选择客户 */
    customerChange(name) {
      const cus = this.customerOptions.find(item => item.customerName === name)
      this.form.customerId = cus ? cus.customerId : undefined
    },
    /** 选择物料回填行数据 */
    materialChange(row) {
      const m = this.materialOptions.find(item => item.materialId === row.materialId)
      if (m) {
        row.materialCode = m.materialCode
        row.materialName = m.materialName
        row.specification = m.specification
        row.unit = m.unit
        row.quantity = row.quantity || 1
        row.price = row.price || undefined
        this.lineCalc(row)
      }
    },
    /** 计算行金额与合计 */
    lineCalc(row) {
      row.amount = Math.round((Number(row.quantity || 0) * Number(row.price || 0)) * 100) / 100
      let total = 0
      this.form.items.forEach(item => {
        total += Number(item.amount || 0)
      })
      this.form.totalAmount = Math.round(total * 100) / 100
    },
    /** 添加明细行 */
    addLine() {
      this.form.items.push({
        itemId: undefined, materialId: undefined, materialCode: undefined, materialName: undefined,
        specification: undefined, unit: undefined, quantity: undefined, price: undefined, amount: 0
      })
    },
    /** 删除明细行 */
    removeLine(index) {
      this.form.items.splice(index, 1)
      this.lineCalc({ amount: 0 })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.items || this.form.items.length === 0) {
            this.$modal.msgWarning("请添加销售明细行")
            return
          }
          if (this.form.orderId != undefined) {
            updateSaleOrder(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSaleOrder(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除销售订单编号为"' + orderIds + '"的数据项？').then(function() {
        return delSaleOrder(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>