<template>
  <div class="app-container">
    <report-card :summary="report.summary" />

    <el-row :gutter="16">
      <el-col :span="14">
        <report-line
          title="销售金额趋势（近7日，单位：元）"
          :dates="report.trend.dates"
          :series="[{ name: '销售金额', data: report.trend.amounts }]"
        />
      </el-col>
      <el-col :span="10">
        <report-bar
          title="产品销售排名（单位：元）"
          :data="report.productRank"
          color="#67c23a"
        />
      </el-col>
    </el-row>

    <el-card shadow="never" class="report-table-card">
      <div slot="header" class="report-chart-header">销售明细</div>
      <el-table :data="report.rows" v-loading="loading">
        <el-table-column label="销售单号" prop="no" align="center" width="170" />
        <el-table-column label="客户" prop="customerName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="产品" prop="productName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="金额(元)" prop="amount" align="center" width="120" />
        <el-table-column label="下单日期" prop="orderDate" align="center" width="120" />
        <el-table-column label="状态" prop="status" align="center" width="100" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getSaleReportData } from "@/api/erp/report"
import ReportCard from "../components/ReportCard"
import ReportLine from "../components/ReportLine"
import ReportBar from "../components/ReportBar"

export default {
  name: "SaleReport",
  components: { ReportCard, ReportLine, ReportBar },
  data() {
    return {
      loading: true,
      report: {
        summary: [],
        trend: { dates: [], amounts: [] },
        productRank: [],
        rows: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getSaleReportData(this.queryParams).then(res => {
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
.report-table-card {
  margin-top: 16px;
}
</style>