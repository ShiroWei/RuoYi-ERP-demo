import request from '@/utils/request'

// 应收账款（只读台账，由销售出库生成）
export function listReceivable(query) {
  return request({ url: '/erp/finance/receivable/list', method: 'get', params: query })
}
export function getReceivable(receivableId) {
  return request({ url: '/erp/finance/receivable/' + receivableId, method: 'get' })
}

// 应付账款（只读台账，由采购入库生成）
export function listPayable(query) {
  return request({ url: '/erp/finance/payable/list', method: 'get', params: query })
}
export function getPayable(payableId) {
  return request({ url: '/erp/finance/payable/' + payableId, method: 'get' })
}

// 收付款单（单头 + 审核流）
export function listPayment(query) {
  return request({ url: '/erp/finance/payment/list', method: 'get', params: query })
}
export function getPayment(paymentId) {
  return request({ url: '/erp/finance/payment/' + paymentId, method: 'get' })
}
export function addPayment(data) {
  return request({ url: '/erp/finance/payment', method: 'post', data: data })
}
export function updatePayment(data) {
  return request({ url: '/erp/finance/payment', method: 'put', data: data })
}
export function delPayment(paymentId) {
  return request({ url: '/erp/finance/payment/' + paymentId, method: 'delete' })
}
export function submitPayment(paymentId) {
  return request({ url: '/erp/finance/payment/submit', method: 'put', params: { paymentId } })
}
export function approvePayment(paymentId) {
  return request({ url: '/erp/finance/payment/approve', method: 'put', params: { paymentId } })
}
export function rejectPayment(paymentId) {
  return request({ url: '/erp/finance/payment/reject', method: 'put', params: { paymentId } })
}
export function completePayment(paymentId) {
  return request({ url: '/erp/finance/payment/complete', method: 'put', params: { paymentId } })
}
