import request from '@/utils/request'

// 物料档案
export function listMaterial(query) {
  return request({ url: '/erp/material/list', method: 'get', params: query })
}
export function getMaterial(materialId) {
  return request({ url: '/erp/material/' + materialId, method: 'get' })
}
export function addMaterial(data) {
  return request({ url: '/erp/material', method: 'post', data: data })
}
export function updateMaterial(data) {
  return request({ url: '/erp/material', method: 'put', data: data })
}
export function delMaterial(materialId) {
  return request({ url: '/erp/material/' + materialId, method: 'delete' })
}

// 供应商档案
export function listSupplier(query) {
  return request({ url: '/erp/supplier/list', method: 'get', params: query })
}
export function getSupplier(supplierId) {
  return request({ url: '/erp/supplier/' + supplierId, method: 'get' })
}
export function addSupplier(data) {
  return request({ url: '/erp/supplier', method: 'post', data: data })
}
export function updateSupplier(data) {
  return request({ url: '/erp/supplier', method: 'put', data: data })
}
export function delSupplier(supplierId) {
  return request({ url: '/erp/supplier/' + supplierId, method: 'delete' })
}

// 客户档案
export function listCustomer(query) {
  return request({ url: '/erp/customer/list', method: 'get', params: query })
}
export function getCustomer(customerId) {
  return request({ url: '/erp/customer/' + customerId, method: 'get' })
}
export function addCustomer(data) {
  return request({ url: '/erp/customer', method: 'post', data: data })
}
export function updateCustomer(data) {
  return request({ url: '/erp/customer', method: 'put', data: data })
}
export function delCustomer(customerId) {
  return request({ url: '/erp/customer/' + customerId, method: 'delete' })
}

// 仓库档案
export function listWarehouse(query) {
  return request({ url: '/erp/warehouse/list', method: 'get', params: query })
}
export function getWarehouse(warehouseId) {
  return request({ url: '/erp/warehouse/' + warehouseId, method: 'get' })
}
export function addWarehouse(data) {
  return request({ url: '/erp/warehouse', method: 'post', data: data })
}
export function updateWarehouse(data) {
  return request({ url: '/erp/warehouse', method: 'put', data: data })
}
export function delWarehouse(warehouseId) {
  return request({ url: '/erp/warehouse/' + warehouseId, method: 'delete' })
}
