<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="流水编号" prop="paymentNo">
        <el-input
          v-model="queryParams.paymentNo"
          placeholder="请输入流水编号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收付款类型" prop="paymentType">
        <el-select v-model="queryParams.paymentType" placeholder="收付款类型" clearable style="width: 160px">
          <el-option v-for="dict in paymentTypeOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="往来单位" prop="partnerName">
        <el-input
          v-model="queryParams.partnerName"
          placeholder="请输入往来单位"
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
          v-hasPermi="['erp:payment:add']"
        >新增记录</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentList">
      <el-table-column label="流水编号" align="center" prop="paymentNo" width="180" />
      <el-table-column label="收付款类型" align="center" prop="paymentType" width="110">
        <template slot-scope="scope">
          <dict-tag :options="paymentTypeOptions" :value="scope.row.paymentType"/>
        </template>
      </el-table-column>
      <el-table-column label="往来单位" align="center" prop="partnerName" :show-overflow-tooltip="true" />
      <el-table-column label="关联单据" align="center" prop="billNo" width="180" />
      <el-table-column label="金额(元)" align="center" prop="amount" width="130" />
      <el-table-column label="收付款日期" align="center" prop="paymentDate" width="120" />
      <el-table-column label="收付款方式" align="center" prop="method" width="130" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="billStatusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" prop="operator" width="130" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-promotion"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:payment:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:payment:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:payment:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:payment:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:payment:edit']"
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

    <!-- 新增收付款记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="收付款类型" prop="paymentType">
              <el-select v-model="form.paymentType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="dict in paymentTypeOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="往来单位" prop="partnerName">
              <el-input v-model="form.partnerName" placeholder="请输入客户或供应商名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="金额" prop="amount">
              <el-input-number v-model="form.amount" :min="0" :precision="2" :controls="false" style="width: 100%" placeholder="金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收付款日期" prop="paymentDate">
              <el-date-picker v-model="form.paymentDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收付款方式" prop="method">
              <el-select v-model="form.method" placeholder="请选择方式" style="width: 100%">
                <el-option label="银行转账" value="银行转账" />
                <el-option label="银行承兑汇票" value="银行承兑汇票" />
                <el-option label="现金" value="现金" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联单据" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入关联单据号" />
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
import { listPayment, addPayment, submitPayment, approvePayment, rejectPayment, completePayment } from "@/api/erp/finance"

export default {
  name: "FinancePayment",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 收付款表格数据
      paymentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 收付款类型字典（接入真实接口后使用 sys_dict 的 erp_payment_type）
      paymentTypeOptions: [
        { value: '1', label: '收款', tagType: 'success' },
        { value: '2', label: '付款', tagType: 'warning' }
      ],
      // 单据状态字典（接入真实接口后使用 sys_dict 的 erp_bill_status）
      billStatusOptions: [
        { value: '0', label: '草稿', tagType: 'info' },
        { value: '1', label: '待审核', tagType: 'warning' },
        { value: '2', label: '审核通过', tagType: 'primary' },
        { value: '3', label: '已驳回', tagType: 'danger' },
        { value: '4', label: '已完成', tagType: 'success' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paymentNo: undefined,
        paymentType: undefined,
        partnerName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paymentType: [
          { required: true, message: "收付款类型不能为空", trigger: "change" }
        ],
        partnerName: [
          { required: true, message: "往来单位不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
        paymentDate: [
          { required: true, message: "收付款日期不能为空", trigger: "change" }
        ],
        method: [
          { required: true, message: "收付款方式不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询收付款列表 */
    getList() {
      this.loading = true
      listPayment(this.queryParams).then(response => {
        this.paymentList = response.rows
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
        paymentId: undefined,
        paymentNo: undefined,
        paymentType: '1',
        partnerName: undefined,
        billNo: undefined,
        paymentDate: undefined,
        amount: undefined,
        method: undefined,
        status: '0',
        operator: '财务部-张会计',
        remark: undefined
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
      this.$modal.confirm('确认提交单据「' + row.paymentNo + '」审核？').then(function() {
        return submitPayment(row.paymentId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过单据「' + row.paymentNo + '」？').then(function() {
        return approvePayment(row.paymentId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回单据「' + row.paymentNo + '」？').then(function() {
        return rejectPayment(row.paymentId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成单据「' + row.paymentNo + '」？').then(function() {
        return completePayment(row.paymentId)
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
      this.title = "新增收付款记录"
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addPayment(this.form).then(() => {
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