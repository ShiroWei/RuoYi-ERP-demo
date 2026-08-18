<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
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
      <el-form-item label="物料分类" prop="category">
        <el-select v-model="queryParams.category" placeholder="物料分类" clearable style="width: 180px">
          <el-option v-for="dict in materialCategoryOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          v-hasPermi="['erp:material:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:material:edit']"
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
          v-hasPermi="['erp:material:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="110" />
      <el-table-column label="物料名称" align="center" prop="materialName" :show-overflow-tooltip="true" />
      <el-table-column label="物料分类" align="center" prop="category" width="100">
        <template slot-scope="scope">
          <dict-tag :options="materialCategoryOptions" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true" />
      <el-table-column label="计量单位" align="center" prop="unit" width="90">
        <template slot-scope="scope">
          <dict-tag :options="unitOptions" :value="scope.row.unit"/>
        </template>
      </el-table-column>
      <el-table-column label="采购单价(元)" align="center" prop="purchasePrice" width="110" />
      <el-table-column label="销售单价(元)" align="center" prop="salePrice" width="110" />
      <el-table-column label="安全库存" align="center" prop="safeStock" width="90" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:material:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:material:remove']"
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

    <!-- 添加或修改物料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料编码" prop="materialCode">
              <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="form.materialName" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料分类" prop="category">
              <el-select v-model="form.category" placeholder="请选择物料分类" style="width: 100%">
                <el-option v-for="dict in materialCategoryOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量单位" prop="unit">
              <el-select v-model="form.unit" placeholder="请选择计量单位" style="width: 100%">
                <el-option v-for="dict in unitOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="规格型号" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格型号" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="采购单价" prop="purchasePrice">
              <el-input-number v-model="form.purchasePrice" :min="0" :precision="2" :controls="false" style="width: 100%" placeholder="采购单价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售单价" prop="salePrice">
              <el-input-number v-model="form.salePrice" :min="0" :precision="2" :controls="false" style="width: 100%" placeholder="销售单价" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安全库存" prop="safeStock">
              <el-input-number v-model="form.safeStock" :min="0" :controls="false" style="width: 100%" placeholder="安全库存" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in statusOptions" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
              </el-radio-group>
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
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial } from "@/api/erp/base"

export default {
  name: "Material",
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
      // 物料表格数据
      materialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 物料分类字典（接入真实接口后使用 sys_dict 的 erp_material_category）
      materialCategoryOptions: [
        { value: '原材料', label: '原材料' },
        { value: '半成品', label: '半成品' },
        { value: '成品', label: '成品' },
        { value: '包装材料', label: '包装材料' },
        { value: '辅料', label: '辅料' }
      ],
      // 计量单位字典（接入真实接口后使用 sys_dict 的 erp_unit）
      unitOptions: [
        { value: '个', label: '个' },
        { value: '台', label: '台' },
        { value: '件', label: '件' },
        { value: '吨', label: '吨' },
        { value: '千克', label: '千克' },
        { value: '米', label: '米' }
      ],
      // 状态字典（sys_normal_disable）
      statusOptions: [
        { value: '0', label: '正常' },
        { value: '1', label: '停用' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: undefined,
        materialName: undefined,
        category: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        category: [
          { required: true, message: "物料分类不能为空", trigger: "change" }
        ],
        unit: [
          { required: true, message: "计量单位不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物料列表 */
    getList() {
      this.loading = true
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows
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
        materialId: undefined,
        materialCode: undefined,
        materialName: undefined,
        category: undefined,
        specification: undefined,
        unit: undefined,
        purchasePrice: undefined,
        salePrice: undefined,
        safeStock: 0,
        status: "0",
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
      this.title = "添加物料"
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.materialId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const materialId = row.materialId || this.ids
      getMaterial(materialId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改物料"
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.materialId != undefined) {
            updateMaterial(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMaterial(this.form).then(() => {
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
      const materialIds = row.materialId || this.ids
      this.$modal.confirm('是否确认删除物料编号为"' + materialIds + '"的数据项？').then(function() {
        return delMaterial(materialIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>