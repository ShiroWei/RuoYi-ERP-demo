import request from '@/utils/request'

// ------------------------------------------------------------
// 一、采购订单（主子表 + 审核流）
// ------------------------------------------------------------
export function listPurchaseOrder(query) {
  return request({ url: '/erp/purchase/order/list', method: 'get', params: query })
}
export function getPurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/' + orderId, method: 'get' })
}
export function addPurchaseOrder(data) {
  return request({ url: '/erp/purchase/order', method: 'post', data: data })
}
export function updatePurchaseOrder(data) {
  return request({ url: '/erp/purchase/order', method: 'put', data: data })
}
export function delPurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/' + orderId, method: 'delete' })
}
export function submitPurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/submit', method: 'put', params: { orderId } })
}
export function approvePurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/approve', method: 'put', params: { orderId } })
}
export function rejectPurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/reject', method: 'put', params: { orderId } })
}
export function completePurchaseOrder(orderId) {
  return request({ url: '/erp/purchase/order/complete', method: 'put', params: { orderId } })
}

// ------------------------------------------------------------
// 二、采购入库单（单头 + 审核流）
// ------------------------------------------------------------
export function listPurchaseInbound(query) {
  return request({ url: '/erp/purchase/inbound/list', method: 'get', params: query })
}
export function getPurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/' + inboundId, method: 'get' })
}
export function addPurchaseInbound(data) {
  return request({ url: '/erp/purchase/inbound', method: 'post', data: data })
}
export function updatePurchaseInbound(data) {
  return request({ url: '/erp/purchase/inbound', method: 'put', data: data })
}
export function delPurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/' + inboundId, method: 'delete' })
}
export function submitPurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/submit', method: 'put', params: { inboundId } })
}
export function approvePurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/approve', method: 'put', params: { inboundId } })
}
export function rejectPurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/reject', method: 'put', params: { inboundId } })
}
export function completePurchaseInbound(inboundId) {
  return request({ url: '/erp/purchase/inbound/complete', method: 'put', params: { inboundId } })
}

// ------------------------------------------------------------
// 三、采购退货单（单头 + 审核流）
// ------------------------------------------------------------
export function listPurchaseReturn(query) {
  return request({ url: '/erp/purchase/return/list', method: 'get', params: query })
}
export function getPurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/' + returnId, method: 'get' })
}
export function addPurchaseReturn(data) {
  return request({ url: '/erp/purchase/return', method: 'post', data: data })
}
export function updatePurchaseReturn(data) {
  return request({ url: '/erp/purchase/return', method: 'put', data: data })
}
export function delPurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/' + returnId, method: 'delete' })
}
export function submitPurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/submit', method: 'put', params: { returnId } })
}
export function approvePurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/approve', method: 'put', params: { returnId } })
}
export function rejectPurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/reject', method: 'put', params: { returnId } })
}
export function completePurchaseReturn(returnId) {
  return request({ url: '/erp/purchase/return/complete', method: 'put', params: { returnId } })
}
