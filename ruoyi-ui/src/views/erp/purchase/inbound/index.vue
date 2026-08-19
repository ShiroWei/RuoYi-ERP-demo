<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="入库单号" prop="inboundNo">
        <el-input
          v-model="queryParams.inboundNo"
          placeholder="请输入入库单号"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
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
          v-hasPermi="['erp:purchaseInbound:add']"
        >新增入库单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:purchaseInbound:edit']"
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
          v-hasPermi="['erp:purchaseInbound:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inboundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单号" align="center" prop="inboundNo" width="160" />
      <el-table-column label="关联订单" align="center" prop="orderNo" width="160" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" :show-overflow-tooltip="true" />
      <el-table-column label="入库仓库" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="入库日期" align="center" prop="inboundDate" width="120" />
      <el-table-column label="入库金额(元)" align="center" prop="totalAmount" width="130" />
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
            v-hasPermi="['erp:purchaseInbound:edit']"
          >提交审核</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:purchaseInbound:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleReject(scope.row)"
            v-hasPermi="['erp:purchaseInbound:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['erp:purchaseInbound:edit']"
          >完成</el-button>
          <el-button
            v-if="scope.row.status === '3'"
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:purchaseInbound:edit']"
          >重新提交</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['erp:purchaseInbound:query']"
          >明细</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:purchaseInbound:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status === '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:purchaseInbound:remove']"
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

    <!-- 添加或修改采购入库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplierName">
              <el-select v-model="form.supplierName" placeholder="请选择供应商" filterable style="width: 100%" @change="supplierChange">
                <el-option v-for="item in supplierOptions" :key="item.supplierId" :label="item.supplierName" :value="item.supplierName" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库仓库" prop="warehouseName">
              <el-select v-model="form.warehouseName" placeholder="请选择仓库" style="width: 100%" @change="warehouseChange">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库日期" prop="inboundDate">
              <el-date-picker v-model="form.inboundDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库金额(元)" prop="totalAmount">
              <el-input-number v-model="form.totalAmount" :min="0" :precision="2" :controls="false" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="单据状态" prop="status">
          <el-select v-model="form.status" placeholder="单据状态" style="width: 100%">
            <el-option v-for="dict in billStatusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 采购入库单明细查看对话框 -->
    <el-dialog title="采购入库单明细" :visible.sync="openDetail" width="760px" append-to-body>
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="入库单号">{{ detail.inboundNo }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{ detail.supplierName }}</el-descriptions-item>
        <el-descriptions-item label="入库仓库">{{ detail.warehouseName }}</el-descriptions-item>
        <el-descriptions-item label="入库日期">{{ detail.inboundDate }}</el-descriptions-item>
        <el-descriptions-item label="单据状态">
          <dict-tag :options="billStatusOptions" :value="detail.status"/>
        </el-descriptions-item>
        <el-descriptions-item label="入库金额">{{ detail.totalAmount }} 元</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { listPurchaseInbound, getPurchaseInbound, delPurchaseInbound, addPurchaseInbound, updatePurchaseInbound, submitPurchaseInbound, approvePurchaseInbound, rejectPurchaseInbound, completePurchaseInbound } from "@/api/erp/purchase"
import { listSupplier, listWarehouse } from "@/api/erp/base"

export default {
  name: "PurchaseInbound",
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
      // 入库单表格数据
      inboundList: [],
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
      // 供应商选项（接入真实接口后动态加载）
      supplierOptions: [],
      // 仓库选项（接入真实接口后动态加载）
      warehouseOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inboundNo: undefined,
        supplierName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        supplierName: [
          { required: true, message: "供应商不能为空", trigger: "change" }
        ],
        warehouseName: [
          { required: true, message: "入库仓库不能为空", trigger: "change" }
        ],
        inboundDate: [
          { required: true, message: "入库日期不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadSupplier()
    this.loadWarehouse()
  },
  methods: {
    /** 加载供应商下拉 */
    loadSupplier() {
      listSupplier({ pageNum: 1, pageSize: 100 }).then(response => {
        this.supplierOptions = response.rows
      })
    },
    /** 加载仓库下拉 */
    loadWarehouse() {
      listWarehouse({ pageNum: 1, pageSize: 100 }).then(response => {
        this.warehouseOptions = response.rows
      })
    },
    /** 选择供应商回填 id */
    supplierChange() {
      const s = this.supplierOptions.find(item => item.supplierName === this.form.supplierName)
      this.form.supplierId = s ? s.supplierId : undefined
    },
    /** 选择仓库回填 id */
    warehouseChange() {
      const w = this.warehouseOptions.find(item => item.warehouseName === this.form.warehouseName)
      this.form.warehouseId = w ? w.warehouseId : undefined
    },
    /** 查询入库单列表 */
    getList() {
      this.loading = true
      listPurchaseInbound(this.queryParams).then(response => {
        this.inboundList = response.rows
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
        inboundId: undefined,
        inboundNo: undefined,
        orderId: undefined,
        orderNo: undefined,
        supplierName: undefined,
        supplierId: undefined,
        warehouseName: undefined,
        warehouseId: undefined,
        inboundDate: undefined,
        status: "0",
        remark: undefined,
        totalAmount: 0
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
      this.title = "新增采购入库单"
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.inboundId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const inboundId = row.inboundId || this.ids
      getPurchaseInbound(inboundId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改采购入库单"
      })
    },
    /** 明细按钮操作 */
    handleDetail(row) {
      this.detail = row
      this.openDetail = true
    },
    /** 提交审核 */
    handleSubmit(row) {
      this.$modal.confirm('确认提交单据「' + row.inboundNo + '」审核？').then(function() {
        return submitPurchaseInbound(row.inboundId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过 */
    handleApprove(row) {
      this.$modal.confirm('确认审核通过单据「' + row.inboundNo + '」？').then(function() {
        return approvePurchaseInbound(row.inboundId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过")
      }).catch(() => {})
    },
    /** 驳回 */
    handleReject(row) {
      this.$modal.confirm('确认驳回单据「' + row.inboundNo + '」？').then(function() {
        return rejectPurchaseInbound(row.inboundId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已驳回")
      }).catch(() => {})
    },
    /** 完成 */
    handleComplete(row) {
      this.$modal.confirm('确认完成单据「' + row.inboundNo + '」？').then(function() {
        return completePurchaseInbound(row.inboundId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("已完成")
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.inboundId != undefined) {
            updatePurchaseInbound(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPurchaseInbound(this.form).then(() => {
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
      const inboundIds = row.inboundId || this.ids
      this.$modal.confirm('是否确认删除入库单编号为"' + inboundIds + '"的数据项？').then(function() {
        return delPurchaseInbound(inboundIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>