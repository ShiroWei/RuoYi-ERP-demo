<template>
  <div class="app-container">
    <report-card :summary="report.summary" />

    <el-row :gutter="16">
      <el-col :span="14">
        <report-line
          title="出入库趋势（近7日，单位：件）"
          :dates="report.inOutTrend.dates"
          :series="[{ name: '入库', data: report.inOutTrend.in }, { name: '出库', data: report.inOutTrend.out }]"
        />
      </el-col>
      <el-col :span="10">
        <report-pie
          title="各仓库库存分布"
          :data="report.warehousePie"
        />
      </el-col>
    </el-row>

    <el-row :gutter="16" class="report-second-row">
      <el-col :span="24">
        <report-bar
          title="库存余量排名（单位：件）"
          :data="report.stockRank"
          color="#e6a23c"
        />
      </el-col>
    </el-row>

    <el-card shadow="never" class="report-table-card">
      <div slot="header" class="report-chart-header">库存明细</div>
      <el-table :data="report.rows" v-loading="loading">
        <el-table-column label="物料编码" prop="code" align="center" width="120" />
        <el-table-column label="物料名称" prop="name" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="仓库" prop="warehouse" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="当前库存" prop="stock" align="center" width="100" />
        <el-table-column label="安全库存" prop="safeStock" align="center" width="100" />
        <el-table-column label="单位" prop="unit" align="center" width="80" />
        <el-table-column label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '不足'" size="mini" type="danger">库存不足</el-tag>
            <el-tag v-else size="mini" type="success">正常</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getStockReportData } from "@/api/erp/report"
import ReportCard from "../components/ReportCard"
import ReportLine from "../components/ReportLine"
import ReportPie from "../components/ReportPie"
import ReportBar from "../components/ReportBar"

export default {
  name: "StockReport",
  components: { ReportCard, ReportLine, ReportPie, ReportBar },
  data() {
    return {
      loading: true,
      report: {
        summary: [],
        stockRank: [],
        warehousePie: [],
        inOutTrend: { dates: [], in: [], out: [] },
        rows: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getStockReportData(this.queryParams).then(res => {
        this.report = res.data
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.report-chart-header {
  font-weight: 600;
}
.report-second-row {
  margin-top: 16px;
}
.report-table-card {
  margin-top: 16px;
}
</style>