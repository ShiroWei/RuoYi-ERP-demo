import request from '@/utils/request'

// 物料清单（BOM，主子表 CRUD）
export function listBom(query) {
  return request({ url: '/erp/production/bom/list', method: 'get', params: query })
}
export function getBom(bomId) {
  return request({ url: '/erp/production/bom/' + bomId, method: 'get' })
}
export function addBom(data) {
  return request({ url: '/erp/production/bom', method: 'post', data: data })
}
export function updateBom(data) {
  return request({ url: '/erp/production/bom', method: 'put', data: data })
}
export function delBom(bomId) {
  return request({ url: '/erp/production/bom/' + bomId, method: 'delete' })
}

// 生产工单（单头 CRUD，无审核流）
export function listWorkOrder(query) {
  return request({ url: '/erp/production/order/list', method: 'get', params: query })
}
export function getWorkOrder(orderId) {
  return request({ url: '/erp/production/order/' + orderId, method: 'get' })
}
export function addWorkOrder(data) {
  return request({ url: '/erp/production/order', method: 'post', data: data })
}
export function updateWorkOrder(data) {
  return request({ url: '/erp/production/order', method: 'put', data: data })
}
export function delWorkOrder(orderId) {
  return request({ url: '/erp/production/order/' + orderId, method: 'delete' })
}
