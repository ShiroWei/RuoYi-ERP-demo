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
  name: 'ReportBar',
  mixins: [resize],
  props: {
    title: { type: String, default: '' },
    height: { type: String, default: '320px' },
    data: { type: Array, required: true },
    color: { type: String, default: '#409eff' }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    data() {
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
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        grid: { left: 55, right: 20, top: 20, bottom: 40 },
        xAxis: {
          type: 'category',
          data: this.data.map(d => d.name),
          axisLabel: { interval: 0, rotate: 20 }
        },
        yAxis: { type: 'value' },
        series: [{
          type: 'bar',
          barWidth: 26,
          itemStyle: { color: this.color, borderRadius: [4, 4, 0, 0] },
          data: this.data.map(d => d.value)
        }]
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