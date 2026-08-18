<template>
  <div class="app-container">
    <report-card :summary="report.summary" />

    <el-row :gutter="16">
      <el-col :span="14">
        <report-line
          title="营收 / 成本 / 毛利趋势（2026年，单位：千元）"
          :dates="report.trend.dates"
          :series="[
            { name: '营收', data: report.trend.revenue },
            { name: '成本', data: report.trend.cost },
            { name: '毛利', data: report.trend.profit }
          ]"
        />
      </el-col>
      <el-col :span="10">
        <report-pie
          title="成本构成"
          :data="report.costPie"
        />
      </el-col>
    </el-row>

    <el-row :gutter="16" class="report-second-row">
      <el-col :span="24">
        <report-bar
          title="产品毛利率（%）"
          :data="report.productProfit"
          color="#f56c6c"
        />
      </el-col>
    </el-row>

    <el-card shadow="never" class="report-table-card">
      <div slot="header" class="report-chart-header">利润明细</div>
      <el-table :data="report.rows" v-loading="loading">
        <el-table-column label="销售单号" prop="no" align="center" width="170" />
        <el-table-column label="客户" prop="customerName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="产品" prop="productName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="营收(元)" prop="revenue" align="center" width="110" />
        <el-table-column label="成本(元)" prop="cost" align="center" width="110" />
        <el-table-column label="毛利(元)" prop="profit" align="center" width="110" />
        <el-table-column label="毛利率" prop="profitRate" align="center" width="90" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getProfitReportData } from "@/api/erp/report"
import ReportCard from "../components/ReportCard"
import ReportLine from "../components/ReportLine"
import ReportPie from "../components/ReportPie"
import ReportBar from "../components/ReportBar"

export default {
  name: "ProfitReport",
  components: { ReportCard, ReportLine, ReportPie, ReportBar },
  data() {
    return {
      loading: true,
      report: {
        summary: [],
        trend: { dates: [], revenue: [], cost: [], profit: [] },
        productProfit: [],
        costPie: [],
        rows: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getProfitReportData(this.queryParams).then(res => {
        this.report = res
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