import request from '@/utils/request'

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

// 待审单据列表（工作台左侧）
// 真实接口：
// export function getTodoList() {
//   return request({ url: '/erp/order/pending', method: 'get' })
// }
export function getTodoList() {
  return Promise.resolve([
    { id: 'PO20260816001', billType: '采购订单', title: '原材料采购订单（钢材）', supplier: '华宇金属材料有限公司', amount: 128000, createTime: '2026-08-16 09:12', priority: '高', status: '待审核' },
    { id: 'SO20260816002', billType: '销售订单', title: '设备销售订单（华东客户）', customer: '华东机械制造有限公司', amount: 86500, createTime: '2026-08-16 08:45', priority: '中', status: '待审核' },
    { id: 'ST20260815003', billType: '库存调拨', title: '调拨单（总仓 → 华东仓）', fromWarehouse: '总仓', amount: 0, createTime: '2026-08-15 16:30', priority: '中', status: '待审核' },
    { id: 'MO20260815004', billType: '生产工单', title: '生产工单（产品A-2026批次）', product: '产品A', amount: 0, createTime: '2026-08-15 14:20', priority: '高', status: '待审核' },
    { id: 'PO20260814005', billType: '采购订单', title: '电子元器件采购', supplier: '深圳联创电子有限公司', amount: 56200, createTime: '2026-08-14 10:20', priority: '低', status: '审核中' }
  ])
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