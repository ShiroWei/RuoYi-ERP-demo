import request from '@/utils/request'
import { listPurchaseOrder, listPurchaseInbound, listPurchaseReturn } from '@/api/erp/purchase'
import { listSaleOrder, listSaleOutbound, listSaleReturn } from '@/api/erp/sale'
import { listStockRecord, listStockCheck, listStockTransfer } from '@/api/erp/stock'
import { listPayment } from '@/api/erp/finance'
import { listWorkOrder } from '@/api/erp/production'

// 首页工作台统计卡片数据
// 当前返回 mock 演示数据；接入真实接口后，改为 request 调用即可：
// export function getPanelData() {
//   return request({
//     url: '/system/dashboard/panel',
//     method: 'get'
//   })
// }
export function getPanelData() {
  return Promise.resolve({
    todoCount: 12,
    purchaseCount: 6,
    saleAmount: 286500,
    stockWarning: 8
  })
}

// 待审单据列表（工作台左侧）：从各业务模块 mock 汇总 status=待审核 的单据，并可点击直达
// 真实接口：
// export function getTodoList() {
//   return request({ url: '/erp/order/pending', method: 'get' })
// }
export function getTodoList() {
  const page = { pageNum: 1, pageSize: 100 }
  return Promise.all([
    listPurchaseOrder(page), listPurchaseInbound(page), listPurchaseReturn(page),
    listSaleOrder(page), listSaleOutbound(page), listSaleReturn(page),
    listStockRecord(page), listStockCheck(page), listStockTransfer(page),
    listPayment(page), listWorkOrder(page)
  ]).then(([po, pi, pr, so, soo, sr, srec, sc, st, pay, wo]) => {
    const todo = []
    const push = (rows, billType, titleFn, amountFn, timeFn, path) => {
      rows.filter(item => String(item.status) === '1').forEach(item => {
        todo.push({
          id: titleFn(item),
          billType,
          title: titleFn(item),
          amount: amountFn(item),
          createTime: timeFn(item),
          status: '待审核',
          path
        })
      })
    }
    push(po.rows, '采购订单', i => i.orderNo + '（' + (i.supplierName || '') + '）', i => i.totalAmount, i => i.createTime, '/erp/purchase/order')
    push(pi.rows, '采购入库', i => i.inboundNo + '（' + (i.supplierName || '') + '）', i => i.totalAmount, i => i.createTime, '/erp/purchase/inbound')
    push(pr.rows, '采购退货', i => i.returnNo, i => i.totalAmount, i => i.createTime, '/erp/purchase/return')
    push(so.rows, '销售订单', i => i.orderNo + '（' + (i.customerName || '') + '）', i => i.totalAmount, i => i.createTime, '/erp/sale/order')
    push(soo.rows, '销售出库', i => i.outboundNo, i => i.totalAmount, i => i.createTime, '/erp/sale/outbound')
    push(sr.rows, '销售退货', i => i.returnNo, i => i.totalAmount, i => i.createTime, '/erp/sale/return')
    push(srec.rows, '出入库记录', i => i.recordNo, i => i.quantity, i => i.recordDate, '/erp/stock/record')
    push(sc.rows, '库存盘点', i => i.checkNo + '（' + (i.warehouseName || '') + '）', () => 0, i => i.createTime || i.checkDate, '/erp/stock/check')
    push(st.rows, '库存调拨', i => i.transferNo, i => i.quantity, i => i.createTime || i.transferDate, '/erp/stock/transfer')
    push(pay.rows, '收付款单', i => i.paymentNo + '（' + (i.partnerName || '') + '）', i => i.amount, i => i.createTime || i.paymentDate, '/erp/finance/payment')
    push(wo.rows, '生产工单', i => i.orderNo + '（' + (i.productName || '') + '）', i => i.quantity, i => i.createTime || i.planStart, '/erp/production/order')
    return todo
  })
}

// 首页折线图数据（近7日销售/采购趋势）
// 真实接口：
// export function getLineChartData() {
//   return request({ url: '/system/dashboard/line', method: 'get' })
// }
export function getLineChartData() {
  return Promise.resolve({
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  })
}

// 首页柱状图数据（各周出入库量）
// 真实接口：
// export function getBarChartData() {
//   return request({ url: '/system/dashboard/bar', method: 'get' })
// }
export function getBarChartData() {
  return Promise.resolve({
    pageA: [30, 42, 35, 51, 49, 62, 69, 91, 126],
    pageB: [20, 32, 25, 41, 39, 52, 59, 71, 96],
    pageC: [10, 22, 15, 31, 29, 42, 49, 61, 76]
  })
}

// 首页饼图数据（业务单据类型分布）
// 真实接口：
// export function getPieChartData() {
//   return request({ url: '/system/dashboard/pie', method: 'get' })
// }
export function getPieChartData() {
  return Promise.resolve({
    series: [
      { value: 335, name: '采购订单' },
      { value: 310, name: '销售订单' },
      { value: 234, name: '生产工单' },
      { value: 135, name: '库存调拨' },
      { value: 148, name: '其他' }
    ]
  })
}

// 首页雷达图数据（运营效率多维评估）
// 真实接口：
// export function getRaddarChartData() {
//   return request({ url: '/system/dashboard/raddar', method: 'get' })
// }
export function getRaddarChartData() {
  return Promise.resolve({
    indicator: [
      { name: '库存周转', max: 100 },
      { name: '销售达成', max: 100 },
      { name: '采购及时', max: 100 },
      { name: '生产完成', max: 100 },
      { name: '资金回笼', max: 100 },
      { name: '客户满意', max: 100 }
    ],
    series: [81, 92, 76, 68, 85, 88]
  })
}