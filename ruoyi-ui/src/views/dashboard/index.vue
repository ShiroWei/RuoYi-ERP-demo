<template>
  <div class="dashboard-editor-container">
    <!-- 顶部欢迎横幅 -->
    <div class="welcome-banner">
      <div class="welcome-info">
        <div class="welcome-title">欢迎回来，{{ name }}</div>
        <div class="welcome-sub">今天是 {{ today }}，开启高效运营管理的一天</div>
      </div>
      <div class="welcome-stats">
        <div class="stat-item">
          <div class="stat-num">{{ panel.todoCount }}</div>
          <div class="stat-label">待审单据</div>
        </div>
        <div class="stat-item">
          <div class="stat-num">{{ panel.purchaseCount }}</div>
          <div class="stat-label">采购执行中</div>
        </div>
        <div class="stat-item">
          <div class="stat-num">{{ formatMoney(panel.saleAmount) }}</div>
          <div class="stat-label">本月销售额</div>
        </div>
        <div class="stat-item">
          <div class="stat-num">{{ panel.stockWarning }}</div>
          <div class="stat-label">库存预警</div>
        </div>
      </div>
    </div>

    <!-- 快捷入口 -->
    <el-row :gutter="16" class="quick-entry-row">
      <el-col :xs="12" :sm="6">
        <div class="quick-entry" @click="handleGo('/erp/purchase/order')">
          <div class="quick-icon icon-purchase"><svg-icon icon-class="shopping" /></div>
          <div class="quick-text">采购订单</div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="6">
        <div class="quick-entry" @click="handleGo('/erp/sale/order')">
          <div class="quick-icon icon-sale"><svg-icon icon-class="money" /></div>
          <div class="quick-text">销售订单</div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="6">
        <div class="quick-entry" @click="handleGo('/erp/stock/inventory')">
          <div class="quick-icon icon-stock"><svg-icon icon-class="list" /></div>
          <div class="quick-text">库存查询</div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="6">
        <div class="quick-entry" @click="handleGo('/erp/production/order')">
          <div class="quick-icon icon-production"><svg-icon icon-class="build" /></div>
          <div class="quick-text">生产工单</div>
        </div>
      </el-col>
    </el-row>

    <!-- 待审单据 + 库存预警/公告 -->
    <el-row :gutter="16" class="workspace-row">
      <el-col :span="14">
        <el-card shadow="never" class="workspace-card">
          <div slot="header" class="card-header">
            <span>待审单据</span>
          </div>
          <div v-loading="todoLoading">
            <div v-for="item in todoList" :key="item.id" class="todo-item" @click="handleGo(item.path)">
              <span class="todo-bill">{{ item.billType }}</span>
              <span class="todo-title">{{ item.title }}</span>
              <span class="todo-time">{{ item.createTime }}</span>
            </div>
            <el-empty v-if="!todoLoading && todoList.length === 0" description="暂无待审单据" :image-size="70" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="10">
        <el-card shadow="never" class="workspace-card">
          <div slot="header" class="card-header">
            <span>库存预警</span>
            <el-button type="text" @click="handleGo('/erp/stock/inventory')">更多</el-button>
          </div>
          <div v-for="w in stockWarnings" :key="w.materialId" class="notice-item">
            <span class="notice-title">{{ w.materialName }}</span>
            <el-tag size="mini" :type="w.type === '不足' ? 'danger' : 'warning'">{{ w.type }}</el-tag>
            <span class="notice-time">{{ w.stock }} / {{ w.safeStock }}</span>
          </div>
          <el-empty v-if="stockWarnings.length === 0" description="库存正常" :image-size="70" />
        </el-card>

        <el-card shadow="never" class="workspace-card notice-card">
          <div slot="header" class="card-header">
            <span>最新公告</span>
          </div>
          <div v-for="n in notices" :key="n.noticeId" class="notice-item">
            <span class="notice-title">{{ n.noticeTitle }}</span>
            <span class="notice-time">{{ n.createTime }}</span>
          </div>
          <el-empty v-if="notices.length === 0" description="暂无公告" :image-size="70" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 统计卡片 -->
    <el-row :gutter="32" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6">
        <div class="card-panel" @click="handleSetLineChartData('sale')">
          <div class="card-panel-icon-wrapper icon-sale-panel">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">今日销售额</div>
            <count-to :start-val="0" :end-val="panel.todaySale" :duration="2600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6">
        <div class="card-panel" @click="handleSetLineChartData('purchase')">
          <div class="card-panel-icon-wrapper icon-purchase-panel">
            <svg-icon icon-class="shopping" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">今日采购额</div>
            <count-to :start-val="0" :end-val="panel.todayPurchase" :duration="3000" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6">
        <div class="card-panel" @click="handleSetLineChartData('todo')">
          <div class="card-panel-icon-wrapper icon-todo-panel">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">待审单据</div>
            <count-to :start-val="0" :end-val="panel.todoCount" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6">
        <div class="card-panel" @click="handleSetLineChartData('warning')">
          <div class="card-panel-icon-wrapper icon-warning-panel">
            <svg-icon icon-class="bell" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">库存预警</div>
            <count-to :start-val="0" :end-val="panel.stockWarning" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="chart-title">近 7 日销售趋势（万元）</div>
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="chart-title">运营效率评估</div>
          <raddar-chart :chart-data="raddarChartData" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="chart-title">业务类型分布</div>
          <pie-chart :chart-data="pieChartData" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="chart-title">各部门出入库量</div>
          <bar-chart :chart-data="barChartData" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'
import LineChart from './LineChart'
import RaddarChart from './RaddarChart'
import PieChart from './PieChart'
import BarChart from './BarChart'
import { getPanelData, getLineChartData, getBarChartData, getPieChartData, getRaddarChartData, getTodoList } from '@/api/dashboard'
import { listNoticeTop } from '@/api/system/notice'
import { listStock } from '@/api/erp/stock'

const defaultLineData = {
  sale: {
    expectedData: [100, 115, 109, 128, 110, 106, 112],
    actualData: [88, 102, 96, 115, 99, 95, 112]
  },
  purchase: {
    expectedData: [120, 118, 125, 116, 122, 128, 108],
    actualData: [105, 102, 108, 100, 106, 110, 108]
  },
  todo: {
    expectedData: [83, 95, 84, 72, 96, 83, 73],
    actualData: [72, 84, 73, 61, 85, 72, 73]
  },
  warning: {
    expectedData: [40, 52, 45, 61, 49, 58, 56],
    actualData: [35, 45, 30, 42, 33, 40, 36]
  }
}

export default {
  name: 'Dashboard',
  components: {
    CountTo,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      name: '管理员',
      today: '',
      panel: {
        todoCount: 0,
        purchaseCount: 0,
        saleAmount: 0,
        stockWarning: 0,
        todaySale: 0,
        todayPurchase: 0
      },
      todoList: [],
      todoLoading: false,
      stockWarnings: [],
      notices: [],
      lineChartData: defaultLineData.sale,
      barChartData: {},
      pieChartData: {},
      raddarChartData: {}
    }
  },
  created() {
    this.today = this.formatToday()
    this.loadData()
    this.loadTodo()
    this.loadStock()
    this.loadNotice()
    this.name = this.$store.state.user.name || '管理员'
  },
  methods: {
    formatToday() {
      const d = new Date()
      const week = ['日', '一', '二', '三', '四', '五', '六']
      return `${d.getFullYear()}年${d.getMonth() + 1}月${d.getDate()}日 星期${week[d.getDay()]}`
    },
    formatMoney(val) {
      if (val >= 10000) {
        return (val / 10000).toFixed(1) + '万'
      }
      return String(val)
    },
    loadData() {
      getPanelData().then(res => {
        this.panel = res
      })
      getLineChartData().then(res => {
        defaultLineData.sale.expectedData = res.expectedData
        defaultLineData.sale.actualData = res.actualData
        this.lineChartData = defaultLineData.sale
      })
      getBarChartData().then(res => {
        this.barChartData = res
      })
      getPieChartData().then(res => {
        this.pieChartData = res
      })
      getRaddarChartData().then(res => {
        this.raddarChartData = res
      })
    },
    loadTodo() {
      this.todoLoading = true
      getTodoList().then(res => {
        this.todoList = (res || []).slice(0, 4)
        this.todoLoading = false
      })
    },
    loadStock() {
      listStock({ pageNum: 1, pageSize: 100 }).then(res => {
        const list = res.rows || []
        const warnings = []
        list.forEach(item => {
          if (item.quantity < item.safeStock) {
            warnings.push({ materialId: item.materialId, materialName: item.materialName, type: '不足', stock: item.quantity, safeStock: item.safeStock })
          } else if (item.quantity > item.safeStock * 3) {
            warnings.push({ materialId: item.materialId, materialName: item.materialName, type: '积压', stock: item.quantity, safeStock: item.safeStock })
          }
        })
        this.stockWarnings = warnings.slice(0, 5)
      })
    },
    loadNotice() {
      listNoticeTop().then(res => {
        const list = res.data || []
        this.notices = Array.isArray(list) ? list.slice(0, 3) : []
      })
    },
    priorityType(priority) {
      if (priority === '高') return 'danger'
      if (priority === '中') return 'warning'
      return 'info'
    },
    handleGo(path) {
      this.$router.push(path)
    },
    handleSetLineChartData(type) {
      this.lineChartData = defaultLineData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 0 16px;

  .welcome-banner {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: linear-gradient(135deg, #1890ff 0%, #36a3f7 100%);
    border-radius: 8px;
    padding: 24px 32px;
    margin: 18px 0 0 0;
    color: #fff;

    .welcome-title {
      font-size: 22px;
      font-weight: 600;
    }

    .welcome-sub {
      margin-top: 8px;
      font-size: 14px;
      opacity: 0.9;
    }

    .welcome-stats {
      display: flex;

      .stat-item {
        text-align: center;
        margin-left: 40px;

        .stat-num {
          font-size: 26px;
          font-weight: 700;
        }

        .stat-label {
          margin-top: 4px;
          font-size: 13px;
          opacity: 0.9;
        }
      }
    }
  }

  .quick-entry-row {
    margin-top: 16px;

    .quick-entry {
      display: flex;
      align-items: center;
      background: #fff;
      border-radius: 8px;
      padding: 18px 20px;
      cursor: pointer;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
      transition: box-shadow 0.2s;

      &:hover {
        box-shadow: 0 4px 16px rgba(24, 144, 255, 0.18);
      }

      .quick-icon {
        width: 42px;
        height: 42px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 22px;
        color: #fff;
        margin-right: 12px;

        &.icon-purchase { background: linear-gradient(135deg, #1890ff, #36a3f7); }
        &.icon-sale { background: linear-gradient(135deg, #13ce66, #36d67e); }
        &.icon-stock { background: linear-gradient(135deg, #ffba00, #ffcf3d); }
        &.icon-production { background: linear-gradient(135deg, #722ed1, #9254de); }
      }

      .quick-text {
        font-weight: 600;
        color: #333;
      }
    }
  }

  .workspace-row {
    margin-top: 16px;

    .workspace-card {
      border: none;
      margin-bottom: 16px;

      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: 600;
      }
    }

    .todo-item {
      display: flex;
      align-items: center;
      padding: 10px 0;
      border-bottom: 1px dashed #ebeef5;
      cursor: pointer;

      &:last-child {
        border-bottom: none;
      }

      &:hover {
        .todo-title {
          color: #1890ff;
        }
      }

      .todo-bill {
        margin-left: 10px;
        color: #909399;
        font-size: 12px;
        width: 72px;
      }

      .todo-title {
        margin: 0 12px;
        flex: 1;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .todo-time {
        color: #909399;
        font-size: 12px;
      }
    }

    .notice-item {
      display: flex;
      align-items: center;
      padding: 8px 0;
      border-bottom: 1px dashed #ebeef5;

      &:last-child {
        border-bottom: none;
      }

      .notice-title {
        flex: 1;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .notice-time {
        color: #909399;
        font-size: 12px;
        margin-left: 8px;
      }
    }
  }

  .panel-group {
    margin-top: 32px;

    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-sale-panel {
          background: #34bfa3;
        }

        .icon-purchase-panel {
          background: #36a3f7;
        }

        .icon-todo-panel {
          background: #f4516c;
        }

        .icon-warning-panel {
          background: #ffba00;
        }
      }

      .icon-sale-panel {
        color: #34bfa3;
      }

      .icon-purchase-panel {
        color: #36a3f7;
      }

      .icon-todo-panel {
        color: #f4516c;
      }

      .icon-warning-panel {
        color: #ffba00;
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }

  .chart-title {
    font-size: 15px;
    font-weight: 600;
    color: #333;
    margin-bottom: 8px;
  }
}

@media (max-width: 900px) {
  .welcome-banner {
    flex-direction: column;

    .welcome-stats {
      margin-top: 16px;

      .stat-item {
        margin-left: 20px;
        margin-right: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>