import request from '@/utils/request'

// ============================================================
// ERP 基础资料 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// ============================================================

// ------------------------------------------------------------
// 一、物料档案
// 真实接口：
// export function listMaterial(query) {
//   return request({ url: '/erp/material/list', method: 'get', params: query })
// }
// export function getMaterial(materialId) {
//   return request({ url: '/erp/material/' + materialId, method: 'get' })
// }
// export function addMaterial(data) {
//   return request({ url: '/erp/material', method: 'post', data: data })
// }
// export function updateMaterial(data) {
//   return request({ url: '/erp/material', method: 'put', data: data })
// }
// export function delMaterial(materialId) {
//   return request({ url: '/erp/material/' + materialId, method: 'delete' })
// }
let materialDb = [
  { materialId: 1, materialCode: 'M1001', materialName: '原材料-钢板', category: '原材料', specification: 'Q235B 2mm*1250mm', unit: '吨', purchasePrice: 4850, salePrice: 5200, safeStock: 500, status: '0', createTime: '2026-08-01 09:00:00', remark: '常用结构钢板' },
  { materialId: 2, materialCode: 'M1002', materialName: '电子元器件', category: '原材料', specification: 'STM32F103C8T6', unit: '个', purchasePrice: 8.5, salePrice: 12, safeStock: 2000, status: '0', createTime: '2026-08-02 10:30:00', remark: '主控芯片' },
  { materialId: 3, materialCode: 'M2001', materialName: '半成品-电机组件', category: '半成品', specification: 'DC24V-300W', unit: '台', purchasePrice: 320, salePrice: 380, safeStock: 200, status: '0', createTime: '2026-08-03 14:20:00', remark: '' },
  { materialId: 4, materialCode: 'M3001', materialName: '成品-产品A', category: '成品', specification: '标准款', unit: '台', purchasePrice: 1200, salePrice: 1600, safeStock: 100, status: '0', createTime: '2026-08-04 11:00:00', remark: '主力产品' },
  { materialId: 5, materialCode: 'M3002', materialName: '成品-产品B', category: '成品', specification: '增强款', unit: '台', purchasePrice: 2200, salePrice: 2800, safeStock: 80, status: '0', createTime: '2026-08-05 16:45:00', remark: '' },
  { materialId: 6, materialCode: 'M4001', materialName: '包装纸箱', category: '包装材料', specification: '60*40*40cm', unit: '个', purchasePrice: 3.5, salePrice: 5, safeStock: 5000, status: '1', createTime: '2026-08-06 09:15:00', remark: '已停用' }
]
export function listMaterial(query) {
  let list = materialDb.slice()
  if (query && query.materialName) {
    list = list.filter(item => item.materialName.indexOf(query.materialName) !== -1)
  }
  if (query && query.materialCode) {
    list = list.filter(item => item.materialCode.indexOf(query.materialCode) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getMaterial(materialId) {
  const row = materialDb.find(item => item.materialId === Number(materialId)) || {}
  return Promise.resolve({ data: row })
}
export function addMaterial(data) {
  data.materialId = materialDb.length ? Math.max(...materialDb.map(item => item.materialId)) + 1 : 1
  data.createTime = formatNow()
  materialDb.unshift(data)
  return Promise.resolve({})
}
export function updateMaterial(data) {
  const idx = materialDb.findIndex(item => item.materialId === data.materialId)
  if (idx !== -1) {
    materialDb[idx] = Object.assign({}, materialDb[idx], data)
  }
  return Promise.resolve({})
}
export function delMaterial(materialId) {
  materialDb = materialDb.filter(item => item.materialId !== Number(materialId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 二、供应商档案
// 真实接口：
// export function listSupplier(query) {
//   return request({ url: '/erp/supplier/list', method: 'get', params: query })
// }
// export function getSupplier(supplierId) {
//   return request({ url: '/erp/supplier/' + supplierId, method: 'get' })
// }
// export function addSupplier(data) {
//   return request({ url: '/erp/supplier', method: 'post', data: data })
// }
// export function updateSupplier(data) {
//   return request({ url: '/erp/supplier', method: 'put', data: data })
// }
// export function delSupplier(supplierId) {
//   return request({ url: '/erp/supplier/' + supplierId, method: 'delete' })
// }
let supplierDb = [
  { supplierId: 1, supplierCode: 'S1001', supplierName: '华宇金属材料有限公司', supplierType: '生产厂家', contactPerson: '王强', phone: '13800001001', email: 'hy@example.com', address: '河北省唐山市高新区', taxNo: '91130000123456789X', bankName: '中国工商银行唐山分行', bankAccount: '622202000011111', status: '0', createTime: '2026-07-20 10:00:00', remark: '钢材主供应商' },
  { supplierId: 2, supplierCode: 'S1002', supplierName: '深圳联创电子有限公司', supplierType: '生产厂家', contactPerson: '李敏', phone: '13800001002', email: 'lc@example.com', address: '广东省深圳市宝安区', taxNo: '9144030012345678XX', bankName: '中国建设银行深圳分行', bankAccount: '622700000022222', status: '0', createTime: '2026-07-21 11:00:00', remark: '电子元器件' },
  { supplierId: 3, supplierCode: 'S1003', supplierName: '上海启明包装有限公司', supplierType: '经销商', contactPerson: '张伟', phone: '13800001003', email: 'qm@example.com', address: '上海市奉贤区', taxNo: '9131000012345678XY', bankName: '招商银行上海分行', bankAccount: '621483000033333', status: '0', createTime: '2026-07-22 09:30:00', remark: '包装材料' },
  { supplierId: 4, supplierCode: 'S1004', supplierName: '广东源丰化工有限公司', supplierType: '生产厂家', contactPerson: '刘洋', phone: '13800001004', email: 'yf@example.com', address: '广东省佛山市南海区', taxNo: '9144060012345678XZ', bankName: '农业银行佛山分行', bankAccount: '622848000044444', status: '1', createTime: '2026-07-23 15:00:00', remark: '已停用' },
  { supplierId: 5, supplierCode: 'S1005', supplierName: '江苏力拓传动有限公司', supplierType: '代理商', contactPerson: '陈杰', phone: '13800001005', email: 'lt@example.com', address: '江苏省苏州市工业园区', taxNo: '9132050012345678XA', bankName: '交通银行苏州分行', bankAccount: '622260000055555', status: '0', createTime: '2026-07-24 13:00:00', remark: '' }
]
export function listSupplier(query) {
  let list = supplierDb.slice()
  if (query && query.supplierName) {
    list = list.filter(item => item.supplierName.indexOf(query.supplierName) !== -1)
  }
  if (query && query.supplierCode) {
    list = list.filter(item => item.supplierCode.indexOf(query.supplierCode) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getSupplier(supplierId) {
  const row = supplierDb.find(item => item.supplierId === Number(supplierId)) || {}
  return Promise.resolve({ data: row })
}
export function addSupplier(data) {
  data.supplierId = supplierDb.length ? Math.max(...supplierDb.map(item => item.supplierId)) + 1 : 1
  data.createTime = formatNow()
  supplierDb.unshift(data)
  return Promise.resolve({})
}
export function updateSupplier(data) {
  const idx = supplierDb.findIndex(item => item.supplierId === data.supplierId)
  if (idx !== -1) {
    supplierDb[idx] = Object.assign({}, supplierDb[idx], data)
  }
  return Promise.resolve({})
}
export function delSupplier(supplierId) {
  supplierDb = supplierDb.filter(item => item.supplierId !== Number(supplierId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 三、客户档案
// 真实接口：
// export function listCustomer(query) {
//   return request({ url: '/erp/customer/list', method: 'get', params: query })
// }
// export function getCustomer(customerId) {
//   return request({ url: '/erp/customer/' + customerId, method: 'get' })
// }
// export function addCustomer(data) {
//   return request({ url: '/erp/customer', method: 'post', data: data })
// }
// export function updateCustomer(data) {
//   return request({ url: '/erp/customer', method: 'put', data: data })
// }
// export function delCustomer(customerId) {
//   return request({ url: '/erp/customer/' + customerId, method: 'delete' })
// }
let customerDb = [
  { customerId: 1, customerCode: 'C1001', customerName: '华东机械制造有限公司', customerType: '企业客户', customerLevel: '黄金', contactPerson: '赵磊', phone: '13900002001', email: 'hdjx@example.com', address: '上海市浦东新区', creditLimit: 500000, status: '0', createTime: '2026-07-15 09:00:00', remark: '大客户' },
  { customerId: 2, customerCode: 'C1002', customerName: '华南电子科技有限公司', customerType: '企业客户', customerLevel: '钻石', contactPerson: '孙丽', phone: '13900002002', email: 'hndz@example.com', address: '广东省深圳市南山区', creditLimit: 800000, status: '0', createTime: '2026-07-16 10:00:00', remark: '战略客户' },
  { customerId: 3, customerCode: 'C1003', customerName: '北方重工集团', customerType: '政府机构', customerLevel: '白银', contactPerson: '周强', phone: '13900002003', email: 'bfzg@example.com', address: '辽宁省沈阳市铁西区', creditLimit: 1000000, status: '0', createTime: '2026-07-17 11:00:00', remark: '' },
  { customerId: 4, customerCode: 'C1004', customerName: '中联建设集团', customerType: '企业客户', customerLevel: '普通', contactPerson: '吴刚', phone: '13900002004', email: 'zljs@example.com', address: '湖北省武汉市江汉区', creditLimit: 300000, status: '0', createTime: '2026-07-18 14:00:00', remark: '' },
  { customerId: 5, customerCode: 'C1005', customerName: '西南轨道交通有限公司', customerType: '政府机构', customerLevel: '黄金', contactPerson: '郑涛', phone: '13900002005', email: 'xngd@example.com', address: '四川省成都市武侯区', creditLimit: 600000, status: '1', createTime: '2026-07-19 15:00:00', remark: '已停用' }
]
export function listCustomer(query) {
  let list = customerDb.slice()
  if (query && query.customerName) {
    list = list.filter(item => item.customerName.indexOf(query.customerName) !== -1)
  }
  if (query && query.customerCode) {
    list = list.filter(item => item.customerCode.indexOf(query.customerCode) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getCustomer(customerId) {
  const row = customerDb.find(item => item.customerId === Number(customerId)) || {}
  return Promise.resolve({ data: row })
}
export function addCustomer(data) {
  data.customerId = customerDb.length ? Math.max(...customerDb.map(item => item.customerId)) + 1 : 1
  data.createTime = formatNow()
  customerDb.unshift(data)
  return Promise.resolve({})
}
export function updateCustomer(data) {
  const idx = customerDb.findIndex(item => item.customerId === data.customerId)
  if (idx !== -1) {
    customerDb[idx] = Object.assign({}, customerDb[idx], data)
  }
  return Promise.resolve({})
}
export function delCustomer(customerId) {
  customerDb = customerDb.filter(item => item.customerId !== Number(customerId))
  return Promise.resolve({})
}

// ------------------------------------------------------------
// 四、仓库档案
// 真实接口：
// export function listWarehouse(query) {
//   return request({ url: '/erp/warehouse/list', method: 'get', params: query })
// }
// export function getWarehouse(warehouseId) {
//   return request({ url: '/erp/warehouse/' + warehouseId, method: 'get' })
// }
// export function addWarehouse(data) {
//   return request({ url: '/erp/warehouse', method: 'post', data: data })
// }
// export function updateWarehouse(data) {
//   return request({ url: '/erp/warehouse', method: 'put', data: data })
// }
// export function delWarehouse(warehouseId) {
//   return request({ url: '/erp/warehouse/' + warehouseId, method: 'delete' })
// }
let warehouseDb = [
  { warehouseId: 1, warehouseCode: 'W001', warehouseName: '总仓-原材料仓', warehouseType: '原材料仓', manager: '钱军', phone: '13700003001', address: '江苏省苏州市工业园区物流园A区', status: '0', createTime: '2026-06-01 09:00:00', remark: '主原料仓' },
  { warehouseId: 2, warehouseCode: 'W002', warehouseName: '半成品仓', warehouseType: '半成品仓', manager: '钱军', phone: '13700003002', address: '江苏省苏州市工业园区物流园B区', status: '0', createTime: '2026-06-02 10:00:00', remark: '' },
  { warehouseId: 3, warehouseCode: 'W003', warehouseName: '成品仓', warehouseType: '成品仓', manager: '唐雪', phone: '13700003003', address: '江苏省苏州市工业园区物流园C区', status: '0', createTime: '2026-06-03 11:00:00', remark: '' },
  { warehouseId: 4, warehouseCode: 'W004', warehouseName: '华东周转仓', warehouseType: '周转仓', manager: '何斌', phone: '13700003004', address: '上海市青浦区华新镇', status: '0', createTime: '2026-06-04 14:00:00', remark: '区域周转' }
]
export function listWarehouse(query) {
  let list = warehouseDb.slice()
  if (query && query.warehouseName) {
    list = list.filter(item => item.warehouseName.indexOf(query.warehouseName) !== -1)
  }
  if (query && query.warehouseCode) {
    list = list.filter(item => item.warehouseCode.indexOf(query.warehouseCode) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function getWarehouse(warehouseId) {
  const row = warehouseDb.find(item => item.warehouseId === Number(warehouseId)) || {}
  return Promise.resolve({ data: row })
}
export function addWarehouse(data) {
  data.warehouseId = warehouseDb.length ? Math.max(...warehouseDb.map(item => item.warehouseId)) + 1 : 1
  data.createTime = formatNow()
  warehouseDb.unshift(data)
  return Promise.resolve({})
}
export function updateWarehouse(data) {
  const idx = warehouseDb.findIndex(item => item.warehouseId === data.warehouseId)
  if (idx !== -1) {
    warehouseDb[idx] = Object.assign({}, warehouseDb[idx], data)
  }
  return Promise.resolve({})
}
export function delWarehouse(warehouseId) {
  warehouseDb = warehouseDb.filter(item => item.warehouseId !== Number(warehouseId))
  return Promise.resolve({})
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}