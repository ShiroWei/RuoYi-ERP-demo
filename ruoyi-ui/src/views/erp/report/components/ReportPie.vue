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
  name: 'ReportPie',
  mixins: [resize],
  props: {
    title: { type: String, default: '' },
    height: { type: String, default: '320px' },
    data: { type: Array, required: true }
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
        tooltip: { trigger: 'item', formatter: '{b}：{c}（{d}%）' },
        legend: { orient: 'vertical', left: 0, top: 'center' },
        series: [{
          type: 'pie',
          radius: ['35%', '65%'],
          center: ['62%', '50%'],
          avoidLabelOverlap: true,
          itemStyle: { borderRadius: 4, borderColor: '#fff', borderWidth: 2 },
          label: { show: true, formatter: '{b}\n{d}%' },
          emphasis: { label: { show: true, fontWeight: 'bold' } },
          data: this.data
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