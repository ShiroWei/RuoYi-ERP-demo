import request from '@/utils/request'

// ============================================================
// ERP 采购管理 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// 字段说明：items 为单据明细行（物料、数量、单价、金额）
// ============================================================

// ------------------------------------------------------------
// 一、采购订单
// 真实接口：
// export function listPurchaseOrder(query) {
//   return request({ url: '/erp/purchase/order/list', method: 'get', params: query })
// }
// export function getPurchaseOrder(orderId) {
//   return request({ url: '/erp/purchase/order/' + orderId, method: 'get' })
// }
// export function addPurchaseOrder(data) {
//   return request({ url: '/erp/purchase/order', method: 'post', data: data })
// }
// export function updatePurchaseOrder(data) {
//   return request({ url: '/erp/purchase/order', method: 'put', data: data })
// }
// export function delPurchaseOrder(orderId) {
//   return request({ url: '/erp/purchase/order/' + orderId, method: 'delete' })
// }
let purchaseOrderDb = [
  {
    orderId: 1, orderNo: 'PO20260818001', supplierId: 1, supplierName: '华宇金属材料有限公司',
    orderDate: '2026-08-10', status: '4', totalAmount: 242500, createTime: '2026-08-10 09:00:00', remark: '月度钢材采购',
    items: [
      { itemId: 1, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 50, price: 4850, amount: 242500 }
    ]
  },
  {
    orderId: 2, orderNo: 'PO20260818002', supplierId: 2, supplierName: '深圳联创电子有限公司',
    orderDate: '2026-08-12', status: '2', totalAmount: 42500, createTime: '2026-08-12 14:00:00', remark: '',
    items: [
      { itemId: 2, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 5000, price: 8.5, amount: 42500 }
    ]
  },
  {
    orderId: 3, orderNo: 'PO20260818003', supplierId: 3, supplierName: '上海启明包装有限公司',
    orderDate: '2026-08-15', status: '1', totalAmount: 17500, createTime: '2026-08-15 10:30:00', remark: '包装纸箱备货',
    items: [
      { itemId: 3, materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', specification: '60*40*40cm', unit: '个', quantity: 5000, price: 3.5, amount: 17500 }
    ]
  },
  {
    orderId: 4, orderNo: 'PO20260818004', supplierId: 5, supplierName: '江苏力拓传动有限公司',
    orderDate: '2026-08-17', status: '0', totalAmount: 96000, createTime: '2026-08-17 16:00:00', remark: '',
    items: [
      { itemId: 4, materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', specification: 'DC24V-300W', unit: '台', quantity: 300, price: 320, amount: 96000 }
    ]
  }
]
export function listPurchaseOrder(query) {
  let list = purchaseOrderDb.slice()
  if (query && query.orderNo) {
    list = list.filter(item => item.orderNo.indexOf(query.orderNo) !== -1)
  }
  if (query && query.supplierName) {
    list = list.filter(item => item.supplierName.indexOf(query.supplierName) !== -1)
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
export function getPurchaseOrder(orderId) {
  const row = purchaseOrderDb.find(item => item.orderId === Number(orderId)) || {}
  return Promise.resolve({ data: row })
}
export function addPurchaseOrder(data) {
  data.orderId = purchaseOrderDb.length ? Math.max(...purchaseOrderDb.map(item => item.orderId)) + 1 : 1
  data.orderNo = 'PO20260818' + String(data.orderId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.orderId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  purchaseOrderDb.unshift(data)
  return Promise.resolve({})
}
export function updatePurchaseOrder(data) {
  const idx = purchaseOrderDb.findIndex(item => item.orderId === data.orderId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.orderId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    purchaseOrderDb[idx] = Object.assign({}, purchaseOrderDb[idx], data)
  }
  return Promise.resolve({})
}
export function delPurchaseOrder(orderId) {
  purchaseOrderDb = purchaseOrderDb.filter(item => item.orderId !== Number(orderId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 二、采购入库单
// 真实接口：
// export function listPurchaseInbound(query) {
//   return request({ url: '/erp/purchase/inbound/list', method: 'get', params: query })
// }
// export function getPurchaseInbound(inboundId) {
//   return request({ url: '/erp/purchase/inbound/' + inboundId, method: 'get' })
// }
// export function addPurchaseInbound(data) {
//   return request({ url: '/erp/purchase/inbound', method: 'post', data: data })
// }
// export function updatePurchaseInbound(data) {
//   return request({ url: '/erp/purchase/inbound', method: 'put', data: data })
// }
// export function delPurchaseInbound(inboundId) {
//   return request({ url: '/erp/purchase/inbound/' + inboundId, method: 'delete' })
// }
let purchaseInboundDb = [
  {
    inboundId: 1, inboundNo: 'IN20260818001', orderId: 1, orderNo: 'PO20260818001',
    supplierName: '华宇金属材料有限公司', warehouseName: '总仓-原材料仓', inboundDate: '2026-08-13',
    status: '4', totalAmount: 242500, createTime: '2026-08-13 15:00:00', remark: '验收入库',
    items: [
      { itemId: 1, materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', specification: 'Q235B 2mm*1250mm', unit: '吨', quantity: 50, price: 4850, amount: 242500 }
    ]
  },
  {
    inboundId: 2, inboundNo: 'IN20260818002', orderId: null, orderNo: '',
    supplierName: '深圳联创电子有限公司', warehouseName: '总仓-原材料仓', inboundDate: '2026-08-16',
    status: '1', totalAmount: 42500, createTime: '2026-08-16 10:00:00', remark: '',
    items: [
      { itemId: 2, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 5000, price: 8.5, amount: 42500 }
    ]
  }
]
export function listPurchaseInbound(query) {
  let list = purchaseInboundDb.slice()
  if (query && query.inboundNo) {
    list = list.filter(item => item.inboundNo.indexOf(query.inboundNo) !== -1)
  }
  if (query && query.supplierName) {
    list = list.filter(item => item.supplierName.indexOf(query.supplierName) !== -1)
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
export function getPurchaseInbound(inboundId) {
  const row = purchaseInboundDb.find(item => item.inboundId === Number(inboundId)) || {}
  return Promise.resolve({ data: row })
}
export function addPurchaseInbound(data) {
  data.inboundId = purchaseInboundDb.length ? Math.max(...purchaseInboundDb.map(item => item.inboundId)) + 1 : 1
  data.inboundNo = 'IN20260818' + String(data.inboundId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.inboundId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  purchaseInboundDb.unshift(data)
  return Promise.resolve({})
}
export function updatePurchaseInbound(data) {
  const idx = purchaseInboundDb.findIndex(item => item.inboundId === data.inboundId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.inboundId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    purchaseInboundDb[idx] = Object.assign({}, purchaseInboundDb[idx], data)
  }
  return Promise.resolve({})
}
export function delPurchaseInbound(inboundId) {
  purchaseInboundDb = purchaseInboundDb.filter(item => item.inboundId !== Number(inboundId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 三、采购退货单
// 真实接口：
// export function listPurchaseReturn(query) {
//   return request({ url: '/erp/purchase/return/list', method: 'get', params: query })
// }
// export function getPurchaseReturn(returnId) {
//   return request({ url: '/erp/purchase/return/' + returnId, method: 'get' })
// }
// export function addPurchaseReturn(data) {
//   return request({ url: '/erp/purchase/return', method: 'post', data: data })
// }
// export function updatePurchaseReturn(data) {
//   return request({ url: '/erp/purchase/return', method: 'put', data: data })
// }
// export function delPurchaseReturn(returnId) {
//   return request({ url: '/erp/purchase/return/' + returnId, method: 'delete' })
// }
let purchaseReturnDb = [
  {
    returnId: 1, returnNo: 'RTPO20260818001', orderId: 2, orderNo: 'PO20260818002',
    supplierName: '深圳联创电子有限公司', returnDate: '2026-08-16',
    status: '4', totalAmount: 2125, createTime: '2026-08-16 17:00:00', remark: '不良品退回',
    items: [
      { itemId: 1, materialId: 2, materialCode: 'M1002', materialName: '电子元器件', specification: 'STM32F103C8T6', unit: '个', quantity: 250, price: 8.5, amount: 2125 }
    ]
  }
]
export function listPurchaseReturn(query) {
  let list = purchaseReturnDb.slice()
  if (query && query.returnNo) {
    list = list.filter(item => item.returnNo.indexOf(query.returnNo) !== -1)
  }
  if (query && query.supplierName) {
    list = list.filter(item => item.supplierName.indexOf(query.supplierName) !== -1)
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
export function getPurchaseReturn(returnId) {
  const row = purchaseReturnDb.find(item => item.returnId === Number(returnId)) || {}
  return Promise.resolve({ data: row })
}
export function addPurchaseReturn(data) {
  data.returnId = purchaseReturnDb.length ? Math.max(...purchaseReturnDb.map(item => item.returnId)) + 1 : 1
  data.returnNo = 'RTPO20260818' + String(data.returnId).padStart(3, '0')
  data.createTime = formatNow()
  let total = 0
  if (data.items) {
    data.items.forEach((item, index) => {
      item.itemId = data.returnId * 100 + index
      total += Number(item.amount || 0)
    })
  }
  data.totalAmount = total
  purchaseReturnDb.unshift(data)
  return Promise.resolve({})
}
export function updatePurchaseReturn(data) {
  const idx = purchaseReturnDb.findIndex(item => item.returnId === data.returnId)
  if (idx !== -1) {
    let total = 0
    if (data.items) {
      data.items.forEach((item, index) => {
        item.itemId = data.returnId * 100 + index
        total += Number(item.amount || 0)
      })
    }
    data.totalAmount = total
    purchaseReturnDb[idx] = Object.assign({}, purchaseReturnDb[idx], data)
  }
  return Promise.resolve({})
}
export function delPurchaseReturn(returnId) {
  purchaseReturnDb = purchaseReturnDb.filter(item => item.returnId !== Number(returnId))
  return Promise.resolve({})
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}