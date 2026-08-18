<template>
  <el-row :gutter="16" class="report-cards">
    <el-col v-for="item in summary" :key="item.label" :span="6">
      <div class="report-card">
        <div class="report-card-label">{{ item.label }}</div>
        <div class="report-card-value">{{ item.value }}</div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'ReportCard',
  props: {
    summary: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
.report-cards {
  margin-bottom: 16px;
}
.report-card {
  padding: 16px;
  background: #fff;
  border-radius: 4px;
  border-left: 3px solid #409eff;
}
.report-card-label {
  font-size: 13px;
  color: #909399;
}
.report-card-value {
  margin-top: 8px;
  font-size: 22px;
  font-weight: 600;
  color: #303133;
}
</style>