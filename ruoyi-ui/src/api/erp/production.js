import request from '@/utils/request'

// ============================================================
// ERP 生产管理 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// ============================================================

// ------------------------------------------------------------
// 一、物料清单（BOM）
// 真实接口：
// export function listBom(query) {
//   return request({ url: '/erp/production/bom/list', method: 'get', params: query })
// }
// export function getBom(bomId) {
//   return request({ url: '/erp/production/bom/' + bomId, method: 'get' })
// }
// export function addBom(data) {
//   return request({ url: '/erp/production/bom', method: 'post', data: data })
// }
// export function updateBom(data) {
//   return request({ url: '/erp/production/bom', method: 'put', data: data })
// }
// export function delBom(bomId) {
//   return request({ url: '/erp/production/bom/' + bomId, method: 'delete' })
// }
let bomDb = [
  {
    bomId: 1, bomCode: 'BOM20260818001', productId: 4, productCode: 'M3001', productName: '成品-产品A',
    productSpec: '标准款', unit: '台', version: 'V1.0', status: '0', createTime: '2026-08-01 09:00:00', remark: '产品A标准BOM',
    items: [
      { itemId: 1, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 0.05 },
      { itemId: 2, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 12 },
      { itemId: 3, materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台', quantity: 1 },
      { itemId: 4, materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', specification: '60*40*40cm', unit: '个', quantity: 1 }
    ]
  },
  {
    bomId: 2, bomCode: 'BOM20260818002', productId: 5, productCode: 'M3002', productName: '成品-产品B',
    productSpec: '增强款', unit: '台', version: 'V1.0', status: '0', createTime: '2026-08-02 10:00:00', remark: '产品B标准BOM',
    items: [
      { itemId: 5, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 0.12 },
      { itemId: 6, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 20 },
      { itemId: 7, materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台', quantity: 2 },
      { itemId: 8, materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', specification: '60*40*40cm', unit: '个', quantity: 1 }
    ]
  },
  {
    bomId: 3, bomCode: 'BOM20260818003', productId: 3, productCode: 'M2001', productName: '半成品-电机组件',
    productSpec: 'DC24V-300W', unit: '台', version: 'V1.1', status: '1', createTime: '2026-08-03 11:00:00', remark: '旧版本已停用',
    items: [
      { itemId: 9, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 0.02 }
    ]
  }
]
export function listBom(query) {
  let list = bomDb.slice()
  if (query && query.bomCode) {
    list = list.filter(item => item.bomCode.indexOf(query.bomCode) !== -1)
  }
  if (query && query.productName) {
    list = list.filter(item => item.productName.indexOf(query.productName) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getBom(bomId) {
  const row = bomDb.find(item => item.bomId === Number(bomId)) || {}
  return Promise.resolve({ data: row })
}
export function addBom(data) {
  data.bomId = bomDb.length ? Math.max(...bomDb.map(item => item.bomId)) + 1 : 1
  data.bomCode = 'BOM20260818' + String(data.bomId).padStart(3, '0')
  data.createTime = formatNow()
  bomDb.unshift(data)
  return Promise.resolve({})
}
export function updateBom(data) {
  const idx = bomDb.findIndex(item => item.bomId === data.bomId)
  if (idx !== -1) {
    data.items.forEach((item, index) => {
      item.itemId = data.bomId * 100 + index
    })
    bomDb[idx] = Object.assign({}, bomDb[idx], data)
  }
  return Promise.resolve({})
}
export function delBom(bomId) {
  bomDb = bomDb.filter(item => item.bomId !== Number(bomId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 二、生产工单
// 真实接口：
// export function listWorkOrder(query) {
//   return request({ url: '/erp/production/order/list', method: 'get', params: query })
// }
// export function getWorkOrder(orderId) {
//   return request({ url: '/erp/production/order/' + orderId, method: 'get' })
// }
// export function addWorkOrder(data) {
//   return request({ url: '/erp/production/order', method: 'post', data: data })
// }
// export function updateWorkOrder(data) {
//   return request({ url: '/erp/production/order', method: 'put', data: data })
// }
// export function delWorkOrder(orderId) {
//   return request({ url: '/erp/production/order/' + orderId, method: 'delete' })
// }
let workOrderDb = [
  {
    orderId: 1, orderNo: 'MO20260818001', productId: 4, productCode: 'M3001', productName: '成品-产品A', productSpec: '标准款', unit: '台',
    quantity: 200, planStart: '2026-08-12', planEnd: '2026-08-20', priority: '1', status: '1', workshop: '一车间', bomCode: 'BOM20260818001', remark: '销售订单SO20260818001',
    items: [
      { itemId: 1, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 10 },
      { itemId: 2, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 2400 }
    ]
  },
  {
    orderId: 2, orderNo: 'MO20260818002', productId: 5, productCode: 'M3002', productName: '成品-产品B', productSpec: '增强款', unit: '台',
    quantity: 80, planStart: '2026-08-15', planEnd: '2026-08-25', priority: '2', status: '0', workshop: '二车间', bomCode: 'BOM20260818002', remark: '',
    items: [
      { itemId: 3, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 1600 }
    ]
  },
  {
    orderId: 3, orderNo: 'MO20260818003', productId: 4, productCode: 'M3001', productName: '成品-产品A', productSpec: '标准款', unit: '台',
    quantity: 100, planStart: '2026-08-18', planEnd: '2026-08-24', priority: '3', status: '2', workshop: '一车间', bomCode: 'BOM20260818001', remark: '已完工入库',
    items: [
      { itemId: 4, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 5 }
    ]
  }
]
export function listWorkOrder(query) {
  let list = workOrderDb.slice()
  if (query && query.orderNo) {
    list = list.filter(item => item.orderNo.indexOf(query.orderNo) !== -1)
  }
  if (query && query.productName) {
    list = list.filter(item => item.productName.indexOf(query.productName) !== -1)
  }
  if (query && query.status !== undefined && query.status !== '') {
    list = list.filter(item => String(item.status) === String(query.status))
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getWorkOrder(orderId) {
  const row = workOrderDb.find(item => item.orderId === Number(orderId)) || {}
  return Promise.resolve({ data: row })
}
export function addWorkOrder(data) {
  data.orderId = workOrderDb.length ? Math.max(...workOrderDb.map(item => item.orderId)) + 1 : 1
  data.orderNo = 'MO20260818' + String(data.orderId).padStart(3, '0')
  workOrderDb.unshift(data)
  return Promise.resolve({})
}
export function updateWorkOrder(data) {
  const idx = workOrderDb.findIndex(item => item.orderId === data.orderId)
  if (idx !== -1) {
    data.items.forEach((item, index) => {
      item.itemId = data.orderId * 100 + index
    })
    workOrderDb[idx] = Object.assign({}, workOrderDb[idx], data)
  }
  return Promise.resolve({})
}
export function delWorkOrder(orderId) {
  workOrderDb = workOrderDb.filter(item => item.orderId !== Number(orderId))
  return Promise.resolve({})
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}