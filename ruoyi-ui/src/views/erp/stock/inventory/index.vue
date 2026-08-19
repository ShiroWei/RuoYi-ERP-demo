<template>
  <div class="app-container">
    <el-alert
      title="提示：库存数量低于安全库存的物料会以红色高亮提示，请及时补货。"
      type="warning"
      :closable="false"
      show-icon
      style="margin-bottom: 12px"
    />

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
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-select v-model="queryParams.warehouseName" placeholder="仓库名称" clearable style="width: 180px">
          <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockList">
      <el-table-column label="仓库名称" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="110" />
      <el-table-column label="物料名称" align="center" prop="materialName" :show-overflow-tooltip="true" />
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true" />
      <el-table-column label="单位" align="center" prop="unit" width="80" />
      <el-table-column label="库存数量" align="center" prop="quantity" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.quantity < scope.row.safeStock ? 'danger' : 'success'" size="mini" effect="plain">
            {{ scope.row.quantity }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="安全库存" align="center" prop="safeStock" width="90" />
      <el-table-column label="库存金额(元)" align="center" prop="amount" width="130" />
      <el-table-column label="库存状态" align="center" width="110">
        <template slot-scope="scope">
          <el-tag :type="scope.row.quantity < scope.row.safeStock ? 'danger' : 'success'" size="mini">
            {{ scope.row.quantity < scope.row.safeStock ? '库存预警' : '库存正常' }}
          </el-tag>
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
  </div>
</template>

<script>
import { listStock } from "@/api/erp/stock"
import { listWarehouse } from "@/api/erp/base"

export default {
  name: "StockInventory",
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存表格数据
      stockList: [],
      // 仓库选项（接入真实接口后动态加载）
      warehouseOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: undefined,
        materialName: undefined,
        warehouseName: undefined
      }
    }
  },
  created() {
    this.getList()
    this.loadWarehouse()
  },
  methods: {
    /** 加载仓库下拉 */
    loadWarehouse() {
      listWarehouse({ pageNum: 1, pageSize: 100 }).then(response => {
        this.warehouseOptions = response.rows
      })
    },
    /** 查询库存列表 */
    getList() {
      this.loading = true
      listStock(this.queryParams).then(response => {
        this.stockList = response.rows
        this.total = response.total
        this.loading = false
      })
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
    }
  }
}
</script>