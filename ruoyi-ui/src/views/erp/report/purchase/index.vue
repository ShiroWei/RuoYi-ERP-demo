<template>
  <div class="app-container">
    <report-card :summary="report.summary" />

    <el-row :gutter="16">
      <el-col :span="14">
        <report-line
          title="采购金额趋势（近7日，单位：元）"
          :dates="report.trend.dates"
          :series="[{ name: '采购金额', data: report.trend.amounts }]"
        />
      </el-col>
      <el-col :span="10">
        <report-pie
          title="供应商采购金额占比"
          :data="report.supplierPie"
        />
      </el-col>
    </el-row>

    <el-card shadow="never" class="report-table-card">
      <div slot="header" class="report-chart-header">采购明细</div>
      <el-table :data="report.rows" v-loading="loading">
        <el-table-column label="采购单号" prop="no" align="center" width="170" />
        <el-table-column label="供应商" prop="supplierName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="物料" prop="materialName" align="center" :show-overflow-tooltip="true" />
        <el-table-column label="金额(元)" prop="amount" align="center" width="120" />
        <el-table-column label="下单日期" prop="orderDate" align="center" width="120" />
        <el-table-column label="状态" prop="status" align="center" width="100" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getPurchaseReportData } from "@/api/erp/report"
import ReportCard from "../components/ReportCard"
import ReportLine from "../components/ReportLine"
import ReportPie from "../components/ReportPie"

export default {
  name: "PurchaseReport",
  components: { ReportCard, ReportLine, ReportPie },
  data() {
    return {
      loading: true,
      report: {
        summary: [],
        trend: { dates: [], amounts: [] },
        supplierPie: [],
        rows: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getPurchaseReportData(this.queryParams).then(res => {
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
.report-table-card {
  margin-top: 16px;
}
</style>