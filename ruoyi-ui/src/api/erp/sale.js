import request from '@/utils/request'

// ============================================================
// ERP 销售管理 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// 字段说明：items 为单据明细行（物料、数量、单价、金额）
// ============================================================

// ------------------------------------------------------------
// 一、销售订单
// 真实接口：
// export function listSaleOrder(query) {
//   return request({ url: '/erp/sale/order/list', method: 'get', params: query })
// }
// export function getSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/' + orderId, method: 'get' })
// }
// export function addSaleOrder(data) {
//   return request({ url: '/erp/sale/order', method: 'post', data: data })
// }
// export function updateSaleOrder(data) {
//   return request({ url: '/erp/sale/order', method: 'put', data: data })
// }
// export function delSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/' + orderId, method: 'delete' })
// }
let saleOrderDb = [
  {
    orderId: 1, orderNo: 'SO20260818001', customerId: 1, customerName: '华东机械制造有限公司',
    orderDate: '2026-08-11', status: '4', totalAmount: 160000, createTime: '2026-08-11 10:00:00', remark: '产品A 100台',
    items: [
      { itemId: 1, materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台', quantity: 100, price: 1600, amount: 160000 }
    ]
  },
  {
    orderId: 2, orderNo: 'SO20260818002', customerId: 2, customerName: '华南电子科技有限公司',
    orderDate: '2026-08-13', status: '2', totalAmount: 84000, createTime: '2026-08-13 14:30:00', remark: '',
    items: [
      { itemId: 2, materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台', quantity: 30, price: 2800, amount: 84000 }
    ]
  },
  {
    orderId: 3, orderNo: 'SO20260818003', customerId: 3, customerName: '北方重工集团',
    orderDate: '2026-08-15', status: '1', totalAmount: 76000, createTime: '2026-08-15 09:30:00', remark: '特批订单',
    items: [
      { itemId: 3, materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台', quantity: 200, price: 380, amount: 76000 }
    ]
  },
  {
    orderId: 4, orderNo: 'SO20260818004', customerId: 4, customerName: '中联建设集团',
    orderDate: '2026-08-17', status: '0', totalAmount: 32000, createTime: '2026-08-17 15:00:00', remark: '',
    items: [
      { itemId: 4, materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台', quantity: 20, price: 1600, amount: 32000 }
    ]
  }
]
export function listSaleOrder(query) {
  let list = saleOrderDb.slice()
  if (query && query.orderNo) {
    list = list.filter(item => item.orderNo.indexOf(query.orderNo) !== -1)
  }
  if (query && query.customerName) {
    list = list.filter(item => item.customerName.indexOf(query.customerName) !== -1)
  }
  if (query && query.status) {
    list = list.filter(item => item.status === query.status)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getSaleOrder(orderId) {
  const row = saleOrderDb.find(item => item.orderId === Number(orderId)) || {}
  return Promise.resolve({ data: row })
}
export function addSaleOrder(data) {
  data.orderId = saleOrderDb.length ? Math.max(...saleOrderDb.map(item => item.orderId)) + 1 : 1
  data.orderNo = 'SO20260818' + String(data.orderId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.orderId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  saleOrderDb.unshift(data)
  return Promise.resolve({})
}
export function updateSaleOrder(data) {
  const idx = saleOrderDb.findIndex(item => item.orderId === data.orderId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.orderId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    saleOrderDb[idx] = Object.assign({}, saleOrderDb[idx], data)
  }
  return Promise.resolve({})
}
export function delSaleOrder(orderId) {
  saleOrderDb = saleOrderDb.filter(item => item.orderId !== Number(orderId))
  return Promise.resolve({})
}
// 单据流程动作（草稿→待审核→审核通过/驳回→完成）
// 真实接口：
// export function submitSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/submit', method: 'put', params: { orderId } })
// }
// export function approveSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/approve', method: 'put', params: { orderId } })
// }
// export function rejectSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/reject', method: 'put', params: { orderId } })
// }
// export function completeSaleOrder(orderId) {
//   return request({ url: '/erp/sale/order/complete', method: 'put', params: { orderId } })
// }
export function submitSaleOrder(orderId) {
  setBillStatus(saleOrderDb, orderId, '1')
  return Promise.resolve({})
}
export function approveSaleOrder(orderId) {
  setBillStatus(saleOrderDb, orderId, '2')
  return Promise.resolve({})
}
export function rejectSaleOrder(orderId) {
  setBillStatus(saleOrderDb, orderId, '3')
  return Promise.resolve({})
}
export function completeSaleOrder(orderId) {
  setBillStatus(saleOrderDb, orderId, '4')
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 二、销售出库单
// 真实接口：
// export function listSaleOutbound(query) {
//   return request({ url: '/erp/sale/outbound/list', method: 'get', params: query })
// }
// export function getSaleOutbound(outboundId) {
//   return request({ url: '/erp/sale/outbound/' + outboundId, method: 'get' })
// }
// export function addSaleOutbound(data) {
//   return request({ url: '/erp/sale/outbound', method: 'post', data: data })
// }
// export function updateSaleOutbound(data) {
//   return request({ url: '/erp/sale/outbound', method: 'put', data: data })
// }
// export function delSaleOutbound(outboundId) {
//   return request({ url: '/erp/sale/outbound/' + outboundId, method: 'delete' })
// }
let saleOutboundDb = [
  {
    outboundId: 1, outboundNo: 'OUT20260818001', orderId: 1, orderNo: 'SO20260818001',
    customerName: '华东机械制造有限公司', warehouseName: '成品仓', outboundDate: '2026-08-14',
    status: '4', totalAmount: 160000, createTime: '2026-08-14 11:00:00', remark: '按约发货',
    items: [
      { itemId: 1, materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', specification: '标准款', unit: '台', quantity: 100, price: 1600, amount: 160000 }
    ]
  },
  {
    outboundId: 2, outboundNo: 'OUT20260818002', orderId: null, orderNo: '',
    customerName: '西南轨道交通有限公司', warehouseName: '成品仓', outboundDate: '2026-08-16',
    status: '1', totalAmount: 56000, createTime: '2026-08-16 16:00:00', remark: '',
    items: [
      { itemId: 2, materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台', quantity: 20, price: 2800, amount: 56000 }
    ]
  }
]
export function listSaleOutbound(query) {
  let list = saleOutboundDb.slice()
  if (query && query.outboundNo) {
    list = list.filter(item => item.outboundNo.indexOf(query.outboundNo) !== -1)
  }
  if (query && query.customerName) {
    list = list.filter(item => item.customerName.indexOf(query.customerName) !== -1)
  }
  if (query && query.status) {
    list = list.filter(item => item.status === query.status)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getSaleOutbound(outboundId) {
  const row = saleOutboundDb.find(item => item.outboundId === Number(outboundId)) || {}
  return Promise.resolve({ data: row })
}
export function addSaleOutbound(data) {
  data.outboundId = saleOutboundDb.length ? Math.max(...saleOutboundDb.map(item => item.outboundId)) + 1 : 1
  data.outboundNo = 'OUT20260818' + String(data.outboundId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.outboundId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  saleOutboundDb.unshift(data)
  return Promise.resolve({})
}
export function updateSaleOutbound(data) {
  const idx = saleOutboundDb.findIndex(item => item.outboundId === data.outboundId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.outboundId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    saleOutboundDb[idx] = Object.assign({}, saleOutboundDb[idx], data)
  }
  return Promise.resolve({})
}
export function delSaleOutbound(outboundId) {
  saleOutboundDb = saleOutboundDb.filter(item => item.outboundId !== Number(outboundId))
  return Promise.resolve({})
}
// 单据流程动作
export function submitSaleOutbound(outboundId) {
  setBillStatus(saleOutboundDb, outboundId, '1')
  return Promise.resolve({})
}
export function approveSaleOutbound(outboundId) {
  setBillStatus(saleOutboundDb, outboundId, '2')
  return Promise.resolve({})
}
export function rejectSaleOutbound(outboundId) {
  setBillStatus(saleOutboundDb, outboundId, '3')
  return Promise.resolve({})
}
export function completeSaleOutbound(outboundId) {
  setBillStatus(saleOutboundDb, outboundId, '4')
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 三、销售退货单
// 真实接口：
// export function listSaleReturn(query) {
//   return request({ url: '/erp/sale/return/list', method: 'get', params: query })
// }
// export function getSaleReturn(returnId) {
//   return request({ url: '/erp/sale/return/' + returnId, method: 'get' })
// }
// export function addSaleReturn(data) {
//   return request({ url: '/erp/sale/return', method: 'post', data: data })
// }
// export function updateSaleReturn(data) {
//   return request({ url: '/erp/sale/return', method: 'put', data: data })
// }
// export function delSaleReturn(returnId) {
//   return request({ url: '/erp/sale/return/' + returnId, method: 'delete' })
// }
let saleReturnDb = [
  {
    returnId: 1, returnNo: 'RTSO20260818001', orderId: 2, orderNo: 'SO20260818002',
    customerName: '华南电子科技有限公司', returnDate: '2026-08-16',
    status: '4', totalAmount: 8400, createTime: '2026-08-16 17:30:00', remark: '外观不良退回',
    items: [
      { itemId: 1, materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', specification: '增强款', unit: '台', quantity: 3, price: 2800, amount: 8400 }
    ]
  }
]
export function listSaleReturn(query) {
  let list = saleReturnDb.slice()
  if (query && query.returnNo) {
    list = list.filter(item => item.returnNo.indexOf(query.returnNo) !== -1)
  }
  if (query && query.customerName) {
    list = list.filter(item => item.customerName.indexOf(query.customerName) !== -1)
  }
  if (query && query.status) {
    list = list.filter(item => item.status === query.status)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getSaleReturn(returnId) {
  const row = saleReturnDb.find(item => item.returnId === Number(returnId)) || {}
  return Promise.resolve({ data: row })
}
export function addSaleReturn(data) {
  data.returnId = saleReturnDb.length ? Math.max(...saleReturnDb.map(item => item.returnId)) + 1 : 1
  data.returnNo = 'RTSO20260818' + String(data.returnId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.returnId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  saleReturnDb.unshift(data)
  return Promise.resolve({})
}
export function updateSaleReturn(data) {
  const idx = saleReturnDb.findIndex(item => item.returnId === data.returnId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.returnId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    saleReturnDb[idx] = Object.assign({}, saleReturnDb[idx], data)
  }
  return Promise.resolve({})
}
export function delSaleReturn(returnId) {
  saleReturnDb = saleReturnDb.filter(item => item.returnId !== Number(returnId))
  return Promise.resolve({})
}
// 单据流程动作
export function submitSaleReturn(returnId) {
  setBillStatus(saleReturnDb, returnId, '1')
  return Promise.resolve({})
}
export function approveSaleReturn(returnId) {
  setBillStatus(saleReturnDb, returnId, '2')
  return Promise.resolve({})
}
export function rejectSaleReturn(returnId) {
  setBillStatus(saleReturnDb, returnId, '3')
  return Promise.resolve({})
}
export function completeSaleReturn(returnId) {
  setBillStatus(saleReturnDb, returnId, '4')
  return Promise.resolve({})
}

// 通用工具：更新单据状态
function setBillStatus(db, id, status) {
  const idx = db.findIndex(item => {
    return (item.orderId !== undefined && item.orderId === Number(id)) ||
      (item.outboundId !== undefined && item.outboundId === Number(id)) ||
      (item.returnId !== undefined && item.returnId === Number(id))
  })
  if (idx !== -1) {
    db[idx].status = status
  }
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}