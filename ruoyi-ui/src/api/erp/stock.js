import request from '@/utils/request'

// 库存台账
export function listStock(query) {
  return request({ url: '/erp/stock/list', method: 'get', params: query })
}
export function getStock(stockId) {
  return request({ url: '/erp/stock/' + stockId, method: 'get' })
}
export function addStock(data) {
  return request({ url: '/erp/stock', method: 'post', data: data })
}
export function updateStock(data) {
  return request({ url: '/erp/stock', method: 'put', data: data })
}
export function delStock(stockId) {
  return request({ url: '/erp/stock/' + stockId, method: 'delete' })
}

// 出入库记录（单头 CRUD，无审核流）
export function listStockRecord(query) {
  return request({ url: '/erp/stock/record/list', method: 'get', params: query })
}
export function getStockRecord(recordId) {
  return request({ url: '/erp/stock/record/' + recordId, method: 'get' })
}
export function addStockRecord(data) {
  return request({ url: '/erp/stock/record', method: 'post', data: data })
}
export function updateStockRecord(data) {
  return request({ url: '/erp/stock/record', method: 'put', data: data })
}
export function delStockRecord(recordId) {
  return request({ url: '/erp/stock/record/' + recordId, method: 'delete' })
}

// 库存盘点（单头 CRUD，无审核流）
export function listStockCheck(query) {
  return request({ url: '/erp/stock/check/list', method: 'get', params: query })
}
export function getStockCheck(checkId) {
  return request({ url: '/erp/stock/check/' + checkId, method: 'get' })
}
export function addStockCheck(data) {
  return request({ url: '/erp/stock/check', method: 'post', data: data })
}
export function updateStockCheck(data) {
  return request({ url: '/erp/stock/check', method: 'put', data: data })
}
export function delStockCheck(checkId) {
  return request({ url: '/erp/stock/check/' + checkId, method: 'delete' })
}

// 库存调拨（单头 + 审核流）
export function listStockTransfer(query) {
  return request({ url: '/erp/stock/transfer/list', method: 'get', params: query })
}
export function getStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/' + transferId, method: 'get' })
}
export function addStockTransfer(data) {
  return request({ url: '/erp/stock/transfer', method: 'post', data: data })
}
export function updateStockTransfer(data) {
  return request({ url: '/erp/stock/transfer', method: 'put', data: data })
}
export function delStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/' + transferId, method: 'delete' })
}
export function submitStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/submit', method: 'put', params: { transferId } })
}
export function approveStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/approve', method: 'put', params: { transferId } })
}
export function rejectStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/reject', method: 'put', params: { transferId } })
}
export function completeStockTransfer(transferId) {
  return request({ url: '/erp/stock/transfer/complete', method: 'put', params: { transferId } })
}
