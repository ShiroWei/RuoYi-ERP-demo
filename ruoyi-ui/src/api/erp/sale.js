import request from '@/utils/request'

// ------------------------------------------------------------
// 一、销售订单（主子表 + 审核流）
// ------------------------------------------------------------
export function listSaleOrder(query) {
  return request({ url: '/erp/sale/order/list', method: 'get', params: query })
}
export function getSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/' + orderId, method: 'get' })
}
export function addSaleOrder(data) {
  return request({ url: '/erp/sale/order', method: 'post', data: data })
}
export function updateSaleOrder(data) {
  return request({ url: '/erp/sale/order', method: 'put', data: data })
}
export function delSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/' + orderId, method: 'delete' })
}
export function submitSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/submit', method: 'put', params: { orderId } })
}
export function approveSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/approve', method: 'put', params: { orderId } })
}
export function rejectSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/reject', method: 'put', params: { orderId } })
}
export function completeSaleOrder(orderId) {
  return request({ url: '/erp/sale/order/complete', method: 'put', params: { orderId } })
}

// ------------------------------------------------------------
// 二、销售出库单（单头 + 审核流）
// ------------------------------------------------------------
export function listSaleOutbound(query) {
  return request({ url: '/erp/sale/outbound/list', method: 'get', params: query })
}
export function getSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/' + outboundId, method: 'get' })
}
export function addSaleOutbound(data) {
  return request({ url: '/erp/sale/outbound', method: 'post', data: data })
}
export function updateSaleOutbound(data) {
  return request({ url: '/erp/sale/outbound', method: 'put', data: data })
}
export function delSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/' + outboundId, method: 'delete' })
}
export function submitSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/submit', method: 'put', params: { outboundId } })
}
export function approveSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/approve', method: 'put', params: { outboundId } })
}
export function rejectSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/reject', method: 'put', params: { outboundId } })
}
export function completeSaleOutbound(outboundId) {
  return request({ url: '/erp/sale/outbound/complete', method: 'put', params: { outboundId } })
}

// ------------------------------------------------------------
// 三、销售退货单（单头 + 审核流）
// ------------------------------------------------------------
export function listSaleReturn(query) {
  return request({ url: '/erp/sale/return/list', method: 'get', params: query })
}
export function getSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/' + returnId, method: 'get' })
}
export function addSaleReturn(data) {
  return request({ url: '/erp/sale/return', method: 'post', data: data })
}
export function updateSaleReturn(data) {
  return request({ url: '/erp/sale/return', method: 'put', data: data })
}
export function delSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/' + returnId, method: 'delete' })
}
export function submitSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/submit', method: 'put', params: { returnId } })
}
export function approveSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/approve', method: 'put', params: { returnId } })
}
export function rejectSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/reject', method: 'put', params: { returnId } })
}
export function completeSaleReturn(returnId) {
  return request({ url: '/erp/sale/return/complete', method: 'put', params: { returnId } })
}
