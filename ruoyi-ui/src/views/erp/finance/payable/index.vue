<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="单据编号" prop="payableNo">
        <el-input
          v-model="queryParams.payableNo"
          placeholder="请输入单据编号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结清状态" prop="settleStatus">
        <el-select v-model="queryParams.settleStatus" placeholder="结清状态" clearable style="width: 160px">
          <el-option v-for="dict in settleStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          type="warning"
          plain
          icon="el-icon-money"
          size="mini"
          @click="handlePay"
        >登记付款</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payableList">
      <el-table-column label="单据编号" align="center" prop="payableNo" width="180" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" :show-overflow-tooltip="true" />
      <el-table-column label="关联单据" align="center" prop="billNo" width="170" />
      <el-table-column label="单据日期" align="center" prop="billDate" width="120" />
      <el-table-column label="应付金额(元)" align="center" prop="amount" width="130" />
      <el-table-column label="已付金额(元)" align="center" prop="paidAmount" width="130" />
      <el-table-column label="未付金额(元)" align="center" prop="unpaidAmount" width="130">
        <template slot-scope="scope">
          <span :style="{ color: scope.row.unpaidAmount > 0 ? '#f56c6c' : '' }">{{ scope.row.unpaidAmount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结清状态" align="center" prop="settleStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="settleStatusOptions" :value="scope.row.settleStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="到期日" align="center" prop="dueDate" width="120" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 登记付款对话框 -->
    <el-dialog title="登记付款" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商名称" prop="partnerName">
          <el-select v-model="form.partnerName" placeholder="请选择供应商" filterable style="width: 100%">
            <el-option v-for="item in supplierOptions" :key="item.supplierId" :label="item.supplierName" :value="item.supplierName" />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="付款金额" prop="amount">
              <el-input-number v-model="form.amount" :min="0" :precision="2" :controls="false" style="width: 100%" placeholder="付款金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="付款日期" prop="paymentDate">
              <el-date-picker v-model="form.paymentDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="付款方式" prop="method">
              <el-select v-model="form.method" placeholder="付款方式" style="width: 100%">
                <el-option label="银行转账" value="银行转账" />
                <el-option label="银行承兑汇票" value="银行承兑汇票" />
                <el-option label="现金" value="现金" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联应付单" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入应付单号" />
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
import { listPayable, addPayment } from "@/api/erp/finance"

export default {
  name: "FinancePayable",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 应付表格数据
      payableList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 结清状态字典（接入真实接口后使用 sys_dict 的 erp_settle_status）
      settleStatusOptions: [
        { value: '0', label: '未结清', tagType: 'info' },
        { value: '1', label: '部分结清', tagType: 'warning' },
        { value: '2', label: '已结清', tagType: 'success' }
      ],
      // 供应商选项（mock）
      supplierOptions: [
        { supplierId: 1, supplierName: '华宇金属材料有限公司' },
        { supplierId: 2, supplierName: '深圳联创电子有限公司' },
        { supplierId: 3, supplierName: '上海启明包装有限公司' },
        { supplierId: 4, supplierName: '广东源丰化工有限公司' },
        { supplierId: 5, supplierName: '江苏力拓传动有限公司' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payableNo: undefined,
        supplierName: undefined,
        settleStatus: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        partnerName: [
          { required: true, message: "供应商不能为空", trigger: "change" }
        ],
        amount: [
          { required: true, message: "付款金额不能为空", trigger: "blur" }
        ],
        paymentDate: [
          { required: true, message: "付款日期不能为空", trigger: "change" }
        ],
        method: [
          { required: true, message: "付款方式不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询应付列表 */
    getList() {
      this.loading = true
      listPayable(this.queryParams).then(response => {
        this.payableList = response.rows
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
        paymentType: '2',
        partnerName: undefined,
        billNo: undefined,
        paymentDate: undefined,
        amount: undefined,
        method: undefined,
        status: '0',
        operator: undefined,
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
    /** 登记付款按钮操作 */
    handlePay() {
      this.reset()
      this.open = true
      this.title = "登记付款"
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.operator = '财务部-张会计'
          addPayment(this.form).then(() => {
            this.$modal.msgSuccess("登记成功")
            this.open = false
            this.getList()
          })
        }
      })
    }
  }
}
</script>