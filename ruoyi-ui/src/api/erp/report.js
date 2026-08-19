import request from '@/utils/request'

// 采购报表
export function getPurchaseReportData(query) {
  return request({ url: '/erp/report/purchase', method: 'get', params: query })
}

// 销售报表
export function getSaleReportData(query) {
  return request({ url: '/erp/report/sale', method: 'get', params: query })
}

// 库存报表
export function getStockReportData(query) {
  return request({ url: '/erp/report/stock', method: 'get', params: query })
}

// 利润报表
export function getProfitReportData(query) {
  return request({ url: '/erp/report/profit', method: 'get', params: query })
}

// 待办统计
export function getPendingCounts() {
  return request({ url: '/erp/order/pending', method: 'get' })
}
