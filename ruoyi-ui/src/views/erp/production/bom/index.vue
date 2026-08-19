<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="BOM编码" prop="bomNo">
        <el-input
          v-model="queryParams.bomNo"
          placeholder="请输入BOM编码"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产成品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产成品名称"
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
          v-hasPermi="['erp:bom:add']"
        >新增BOM</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:bom:edit']"
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
          v-hasPermi="['erp:bom:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.items" size="mini">
            <el-table-column label="物料编码" prop="materialCode" align="center" width="120" />
            <el-table-column label="物料名称" prop="materialName" align="center" min-width="180" />
            <el-table-column label="规格型号" prop="specification" align="center" min-width="180" />
            <el-table-column label="单位" prop="unit" align="center" width="70" />
            <el-table-column label="单耗用量" prop="quantity" align="center" width="110" />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="BOM编码" align="center" prop="bomNo" width="170" />
      <el-table-column label="产成品编码" align="center" prop="productCode" width="110" />
      <el-table-column label="产成品名称" align="center" prop="productName" :show-overflow-tooltip="true" />
      <el-table-column label="单位" align="center" prop="unit" width="70" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['erp:bom:query']"
          >明细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:bom:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:bom:remove']"
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

    <!-- 添加或修改BOM对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="产成品" prop="productId">
              <el-select v-model="form.productId" placeholder="请选择产成品" filterable style="width: 100%" @change="productChange">
                <el-option v-for="m in productOptions" :key="m.materialId" :label="m.materialName" :value="m.materialId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in statusOptions" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="left">物料清单（单耗用量）</el-divider>
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
          <el-table-column label="单耗用量" min-width="110" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.quantity" :min="0" :precision="4" :controls="false" size="mini" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="70" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="removeLine(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; text-align: right">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="addLine">添加物料行</el-button>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- BOM明细查看对话框 -->
    <el-dialog title="BOM明细" :visible.sync="openDetail" width="760px" append-to-body>
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="BOM编码">{{ detail.bomNo }}</el-descriptions-item>
        <el-descriptions-item label="产成品">{{ detail.productName }}</el-descriptions-item>
        <el-descriptions-item label="单位">{{ detail.unit }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="statusOptions" :value="detail.status"/>
        </el-descriptions-item>
        <el-descriptions-item label="备注">{{ detail.remark }}</el-descriptions-item>
      </el-descriptions>
      <el-table :data="detail.items" border size="mini" style="margin-top: 12px">
        <el-table-column label="物料编码" prop="materialCode" align="center" width="120" />
        <el-table-column label="物料名称" prop="materialName" align="center" min-width="180" />
        <el-table-column label="规格型号" prop="specification" align="center" min-width="160" />
        <el-table-column label="单位" prop="unit" align="center" width="70" />
        <el-table-column label="单耗用量" prop="quantity" align="center" width="110" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listBom, getBom, delBom, addBom, updateBom } from "@/api/erp/production"
import { listMaterial } from "@/api/erp/base"

export default {
  name: "ProductionBom",
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
      // BOM表格数据
      bomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示明细
      openDetail: false,
      // 明细数据
      detail: {},
      // 状态字典（sys_normal_disable）
      statusOptions: [
        { value: '0', label: '正常' },
        { value: '1', label: '停用' }
      ],
      // 产成品选项（接入真实接口后动态加载）
      productOptions: [],
      // 物料选项（接入真实接口后动态加载）
      materialOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bomNo: undefined,
        productName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "产成品不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.loadMaterial()
  },
  methods: {
    /** 加载物料下拉（产成品与物料清单共用） */
    loadMaterial() {
      listMaterial({ pageNum: 1, pageSize: 100 }).then(response => {
        this.materialOptions = response.rows
        this.productOptions = response.rows
      })
    },
    /** 查询BOM列表 */
    getList() {
      this.loading = true
      listBom(this.queryParams).then(response => {
        this.bomList = response.rows
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
        bomId: undefined,
        bomNo: undefined,
        productId: undefined,
        productCode: undefined,
        productName: undefined,
        unit: undefined,
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
      this.title = "新增BOM"
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.bomId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const bomId = row.bomId || this.ids
      getBom(bomId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改BOM"
      })
    },
    /** 明细按钮操作 */
    handleDetail(row) {
      this.detail = row
      this.openDetail = true
    },
    /** 选择产成品回填数据 */
    productChange(productId) {
      const m = this.productOptions.find(item => item.materialId === productId)
      if (m) {
        this.form.productCode = m.materialCode
        this.form.productName = m.materialName
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
    /** 添加物料行 */
    addLine() {
      this.form.items.push({
        itemId: undefined, materialId: undefined, materialCode: undefined, materialName: undefined,
        specification: undefined, unit: undefined, quantity: undefined
      })
    },
    /** 删除物料行 */
    removeLine(index) {
      this.form.items.splice(index, 1)
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.items || this.form.items.length === 0) {
            this.$modal.msgWarning("请添加物料清单行")
            return
          }
          if (this.form.bomId != undefined) {
            updateBom(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBom(this.form).then(() => {
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
      const bomIds = row.bomId || this.ids
      this.$modal.confirm('是否确认删除BOM编号为"' + bomIds + '"的数据项？').then(function() {
        return delBom(bomIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>