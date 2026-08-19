import request from '@/utils/request'
import { getPurchaseReportData, getSaleReportData, getStockReportData, getPendingCounts } from '@/api/erp/report'
import { listPurchaseOrder, listPurchaseInbound } from '@/api/erp/purchase'
import { listSaleOrder, listSaleOutbound } from '@/api/erp/sale'
import { listStockRecord, listStockCheck, listStockTransfer, listStock } from '@/api/erp/stock'
import { listPayment } from '@/api/erp/finance'
import { listWorkOrder, listBom } from '@/api/erp/production'
import { listSupplier, listCustomer } from '@/api/erp/base'

const pendingPath = {
  '采购订单': '/erp/purchase/order',
  '销售订单': '/erp/sale/order',
  '库存调拨': '/erp/stock/transfer',
  '收付款单': '/erp/finance/payment'
}

// 待审单据列表（来自真实待办统计）
export function getTodoList() {
  return getPendingCounts().then(res => {
    const items = (res && res.data && res.data.items) || []
    return items.map(item => ({
      id: item.name,
      billType: item.name,
      title: `${item.name}（${item.count} 张）`,
      amount: item.count,
      createTime: '',
      status: '待审核',
      path: pendingPath[item.name] || '/erp/purchase/order'
    }))
  })
}

// summary 固定顺序取值：purchase[总额,订单数,供应商数,均额] sale[总额,订单数,客户数,均额] stock[总量,总值,预警数,仓数]
const valueOf = (summary, index) => {
  const item = (summary || [])[index]
  return item ? Number(item.value || 0) : 0
}

// 首页统计卡片
export function getPanelData() {
  const pendingPromise = getPendingCounts().then(res => (res && res.data && res.data.items) || [])
  const salePromise = getSaleReportData()
  const purchasePromise = getPurchaseReportData()
  const stockPromise = listStock({ pageNum: 1, pageSize: 1000 })
  return Promise.all([pendingPromise, salePromise, purchasePromise, stockPromise]).then(([items, sale, purchase, stock]) => {
    let todoCount = 0
    let purchaseCount = 0
    items.forEach(item => {
      todoCount += Number(item.count || 0)
      if (item.name === '采购订单') {
        purchaseCount = Number(item.count || 0)
      }
    })
    const saleAmount = valueOf(sale.data.summary, 0)
    const todayPurchase = valueOf(purchase.data.summary, 0)
    const stockWarning = (stock.rows || []).filter(item => Number(item.quantity) < Number(item.safeStock)).length
    return {
      todoCount,
      purchaseCount,
      saleAmount,
      stockWarning,
      todaySale: saleAmount,
      todayPurchase
    }
  })
}

// 近7日销售/采购趋势
export function getLineChartData() {
  return Promise.all([getSaleReportData(), getPurchaseReportData()]).then(([sale, purchase]) => {
    const saleAmounts = ((sale.data.trend && sale.data.trend.amounts) || []).map(v => Math.round(Number(v) / 10000 * 100) / 100)
    const purchaseAmounts = ((purchase.data.trend && purchase.data.trend.amounts) || []).map(v => Math.round(Number(v) / 10000 * 100) / 100)
    return {
      expectedData: saleAmounts,
      actualData: purchaseAmounts
    }
  })
}

// 近7日出入库量
export function getBarChartData() {
  return getStockReportData().then(res => {
    const trend = (res && res.data && res.data.inOutTrend) || {}
    return {
      pageA: (trend.in || []).map(v => Number(v)),
      pageB: (trend.out || []).map(v => Number(v)),
      pageC: (trend.in || []).map(v => 0)
    }
  })
}

// 单据类型分布
export function getPieChartData() {
  const page = { pageNum: 1, pageSize: 1 }
  return Promise.all([
    listPurchaseOrder(page), listSaleOrder(page), listStockRecord(page),
    listPayment(page), listWorkOrder(page), listPurchaseInbound(page), listSaleOutbound(page)
  ]).then(([po, so, srec, pay, wo, pi, soo]) => {
    const series = []
    const push = (name, total) => {
      if (total > 0) series.push({ value: total, name })
    }
    push('采购订单', po.total)
    push('采购入库', pi.total)
    push('销售订单', so.total)
    push('销售出库', soo.total)
    push('出入库记录', srec.total)
    push('收付款单', pay.total)
    push('生产工单', wo.total)
    if (series.length === 0) {
      series.push({ value: 1, name: '暂无数据' })
    }
    return { series }
  })
}

// 运营效率评估（基于真实汇总数据推导）
export function getRaddarChartData() {
  return Promise.all([getSaleReportData(), getPurchaseReportData(), getStockReportData(), getPendingCounts(), listSupplier({ pageNum: 1, pageSize: 1 }), listCustomer({ pageNum: 1, pageSize: 1 })]).then(([sale, purchase, stock, pending, supplier, customer]) => {
    let pendingTotal = 0
    ;(pending.data.items || []).forEach(i => { pendingTotal += Number(i.count || 0) })
    const stockTotal = valueOf(stock.data.summary, 0)
    const stockLow = valueOf(stock.data.summary, 2)
    const calc = (val, max) => Math.max(0, Math.min(100, Math.round((val || 0) / (max || 1) * 100)))
    const indicator = [
      { name: '库存周转', max: 100 },
      { name: '销售达成', max: 100 },
      { name: '采购效率', max: 100 },
      { name: '审批效率', max: 100 },
      { name: '库存安全', max: 100 },
      { name: '客户覆盖', max: 100 }
    ]
    const series = [
      calc(stockTotal, 200000),
      calc(valueOf(sale.data.summary, 0), 500000),
      calc(valueOf(purchase.data.summary, 0), 500000),
      Math.max(20, 100 - pendingTotal * 10),
      Math.max(20, 100 - stockLow * 15),
      calc(customer.total, 50)
    ]
    return { indicator, series }
  })
}
