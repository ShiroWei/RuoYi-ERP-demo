<template>
  <el-card shadow="never" class="report-chart-card">
    <div slot="header" class="report-chart-header">{{ title }}</div>
    <div ref="chart" class="report-chart" :style="{ height }" />
  </el-card>
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import resize from '@/views/dashboard/mixins/resize'

export default {
  name: 'ReportLine',
  mixins: [resize],
  props: {
    title: { type: String, default: '' },
    height: { type: String, default: '320px' },
    dates: { type: Array, required: true },
    series: { type: Array, required: true }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    dates() {
      this.setChart()
    },
    series() {
      this.setChart()
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.chart)
      this.setChart()
    },
    setChart() {
      if (!this.chart) {
        return
      }
      this.chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: this.series.map(s => s.name), top: 0 },
        grid: { left: 55, right: 20, top: 32, bottom: 30 },
        xAxis: { type: 'category', boundaryGap: false, data: this.dates },
        yAxis: { type: 'value' },
        series: this.series.map(s => ({ name: s.name, type: 'line', smooth: true, data: s.data }))
      })
    }
  }
}
</script>

<style scoped>
.report-chart-header {
  font-weight: 600;
}
</style>