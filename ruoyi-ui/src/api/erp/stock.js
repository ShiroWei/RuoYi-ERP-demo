import request from '@/utils/request'

// ============================================================
// ERP 库存管理 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// ============================================================

// ------------------------------------------------------------
// 一、库存查询（库存台账）
// 真实接口：
// export function listStock(query) {
//   return request({ url: '/erp/stock/list', method: 'get', params: query })
// }
let stockDb = [
  { stockId: 1, warehouseId: 1, warehouseName: '总仓-原材料仓', materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 320, safeStock: 500, amount: 1552000 },
  { stockId: 2, warehouseId: 1, warehouseName: '总仓-原材料仓', materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 6800, safeStock: 2000, amount: 57800 },
  { stockId: 3, warehouseId: 1, warehouseName: '总仓-原材料仓', materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', specification: '60*40*40cm', unit: '个', quantity: 4200, safeStock: 5000, amount: 14700 },
  { stockId: 4, warehouseId: 2, warehouseName: '半成品仓', materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台', quantity: 150, safeStock: 200, amount: 48000 },
  { stockId: 5, warehouseId: 3, warehouseName: '成品仓', materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台', quantity: 86, safeStock: 100, amount: 137600 },
  { stockId: 6, warehouseId: 3, warehouseName: '成品仓', materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台', quantity: 145, safeStock: 80, amount: 406000 }
]
export function listStock(query) {
  let list = stockDb.slice()
  if (query && query.materialName) {
    list = list.filter(item => item.materialName.indexOf(query.materialName) !== -1)
  }
  if (query && query.materialCode) {
    list = list.filter(item => item.materialCode.indexOf(query.materialCode) !== -1)
  }
  if (query && query.warehouseName) {
    list = list.filter(item => item.warehouseName.indexOf(query.warehouseName) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}

// ------------------------------------------------------------
// 二、出入库记录
// 真实接口：
// export function listStockRecord(query) {
//   return request({ url: '/erp/stock/record/list', method: 'get', params: query })
// }
// export function addStockRecord(data) {
//   return request({ url: '/erp/stock/record', method: 'post', data: data })
// }
let stockRecordDb = [
  { recordId: 1, recordNo: 'SR20260818001', recordType: '1', materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', unit: '吨', quantity: 50, warehouseName: '总仓-原材料仓', relatedBill: 'PO20260818001', operator: '钱军', recordDate: '2026-08-13', status: '4', remark: '采购入库' },
  { recordId: 2, recordNo: 'SR20260818002', recordType: '2', materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', unit: '台', quantity: 100, warehouseName: '成品仓', relatedBill: 'SO20260818001', operator: '唐雪', recordDate: '2026-08-14', status: '4', remark: '销售出库' },
  { recordId: 3, recordNo: 'SR20260818003', recordType: '8', materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', unit: '台', quantity: 30, warehouseName: '华东周转仓', relatedBill: 'TF20260818001', operator: '何斌', recordDate: '2026-08-15', status: '2', remark: '调拨出库' },
  { recordId: 4, recordNo: 'SR20260818004', recordType: '4', materialId: 2, materialCode: 'M1002', materialName: '电子元器件', unit: '个', quantity: 1200, warehouseName: '总仓-原材料仓', relatedBill: 'MO20260818002', operator: '钱军', recordDate: '2026-08-16', status: '1', remark: '生产领料' }
]
export function listStockRecord(query) {
  let list = stockRecordDb.slice()
  if (query && query.recordNo) {
    list = list.filter(item => item.recordNo.indexOf(query.recordNo) !== -1)
  }
  if (query && query.recordType) {
    list = list.filter(item => item.recordType === query.recordType)
  }
  if (query && query.warehouseName) {
    list = list.filter(item => item.warehouseName.indexOf(query.warehouseName) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function addStockRecord(data) {
  data.recordId = stockRecordDb.length ? Math.max(...stockRecordDb.map(item => item.recordId)) + 1 : 1
  data.recordNo = 'SR20260818' + String(data.recordId).padStart(3, '0')
  stockRecordDb.unshift(data)
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 三、库存盘点
// 真实接口：
// export function listStockCheck(query) {
//   return request({ url: '/erp/stock/check/list', method: 'get', params: query })
// }
// export function getStockCheck(checkId) {
//   return request({ url: '/erp/stock/check/' + checkId, method: 'get' })
// }
// export function addStockCheck(data) {
//   return request({ url: '/erp/stock/check', method: 'post', data: data })
// }
// export function updateStockCheck(data) {
//   return request({ url: '/erp/stock/check', method: 'put', data: data })
// }
// export function delStockCheck(checkId) {
//   return request({ url: '/erp/stock/check/' + checkId, method: 'delete' })
// }
let stockCheckDb = [
  {
    checkId: 1, checkNo: 'CK20260818001', warehouseName: '成品仓', checkDate: '2026-08-15',
    status: '1', checkUser: '唐雪', remark: '月末盘点',
    items: [
      { itemId: 1, materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台', bookQty: 86, checkQty: 85, diffQty: -1 },
      { itemId: 2, materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台', bookQty: 145, checkQty: 145, diffQty: 0 }
    ]
  },
  {
    checkId: 2, checkNo: 'CK20260818002', warehouseName: '总仓-原材料仓', checkDate: '2026-08-16',
    status: '0', checkUser: '钱军', remark: '待盘点',
    items: [
      { itemId: 3, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', bookQty: 320, checkQty: undefined, diffQty: undefined },
      { itemId: 4, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', bookQty: 6800, checkQty: undefined, diffQty: undefined }
    ]
  }
]
export function listStockCheck(query) {
  let list = stockCheckDb.slice()
  if (query && query.checkNo) {
    list = list.filter(item => item.checkNo.indexOf(query.checkNo) !== -1)
  }
  if (query && query.warehouseName) {
    list = list.filter(item => item.warehouseName.indexOf(query.warehouseName) !== -1)
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
export function getStockCheck(checkId) {
  const row = stockCheckDb.find(item => item.checkId === Number(checkId)) || {}
  return Promise.resolve({ data: row })
}
export function addStockCheck(data) {
  data.checkId = stockCheckDb.length ? Math.max(...stockCheckDb.map(item => item.checkId)) + 1 : 1
  data.checkNo = 'CK20260818' + String(data.checkId).padStart(3, '0')
  stockCheckDb.unshift(data)
  return Promise.resolve({})
}
export function updateStockCheck(data) {
  const idx = stockCheckDb.findIndex(item => item.checkId === data.checkId)
  if (idx !== -1) {
    data.items.forEach(item => {
      item.diffQty = (Number(item.checkQty || 0) - Number(item.bookQty || 0))
    })
    stockCheckDb[idx] = Object.assign({}, stockCheckDb[idx], data)
  }
  return Promise.resolve({})
}
export function delStockCheck(checkId) {
  stockCheckDb = stockCheckDb.filter(item => item.checkId !== Number(checkId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 四、库存调拨
// 真实接口：
// export function listStockTransfer(query) {
//   return request({ url: '/erp/stock/transfer/list', method: 'get', params: query })
// }
// export function addStockTransfer(data) {
//   return request({ url: '/erp/stock/transfer', method: 'post', data: data })
// }
let stockTransferDb = [
  { transferId: 1, transferNo: 'TF20260818001', materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', unit: '台', quantity: 30, fromWarehouse: '成品仓', toWarehouse: '华东周转仓', transferDate: '2026-08-15', status: '2', operator: '何斌', remark: '区域调拨' },
  { transferId: 2, transferNo: 'TF20260818002', materialId: 2, materialCode: 'M1002', materialName: '电子元器件', unit: '个', quantity: 1000, fromWarehouse: '总仓-原材料仓', toWarehouse: '华东周转仓', transferDate: '2026-08-16', status: '1', operator: '何斌', remark: '' }
]
export function listStockTransfer(query) {
  let list = stockTransferDb.slice()
  if (query && query.transferNo) {
    list = list.filter(item => item.transferNo.indexOf(query.transferNo) !== -1)
  }
  if (query && query.materialName) {
    list = list.filter(item => item.materialName.indexOf(query.materialName) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function addStockTransfer(data) {
  data.transferId = stockTransferDb.length ? Math.max(...stockTransferDb.map(item => item.transferId)) + 1 : 1
  data.transferNo = 'TF20260818' + String(data.transferId).padStart(3, '0')
  stockTransferDb.unshift(data)
  return Promise.resolve({})
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}