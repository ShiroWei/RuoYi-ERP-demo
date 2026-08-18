import request from '@/utils/request'

// ============================================================
// ERP 财务管理 API
// 当前全部为 mock 演示数据（内存数组，支持前端增删改查）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// ============================================================

// ------------------------------------------------------------
// 一、应收账款
// 真实接口：
// export function listReceivable(query) {
//   return request({ url: '/erp/finance/receivable/list', method: 'get', params: query })
// }
let receivableDb = [
  { receivableId: 1, receivableNo: 'AR20260818001', customerName: '华东机械制造有限公司', billNo: 'SO20260818001', billDate: '2026-08-11', amount: 160000, paidAmount: 96000, unpaidAmount: 64000, settleStatus: '1', dueDate: '2026-09-10', remark: '首付款已收' },
  { receivableId: 2, receivableNo: 'AR20260818002', customerName: '华南电子科技有限公司', billNo: 'SO20260818002', billDate: '2026-08-13', amount: 84000, paidAmount: 0, unpaidAmount: 84000, settleStatus: '0', dueDate: '2026-09-12', remark: '' },
  { receivableId: 3, receivableNo: 'AR20260818003', customerName: '北方重工集团', billNo: 'SO20260818003', billDate: '2026-08-15', amount: 76000, paidAmount: 76000, unpaidAmount: 0, settleStatus: '2', dueDate: '2026-09-14', remark: '已结清' }
]
export function listReceivable(query) {
  let list = receivableDb.slice()
  if (query && query.receivableNo) {
    list = list.filter(item => item.receivableNo.indexOf(query.receivableNo) !== -1)
  }
  if (query && query.customerName) {
    list = list.filter(item => item.customerName.indexOf(query.customerName) !== -1)
  }
  if (query && query.settleStatus !== undefined && query.settleStatus !== '') {
    list = list.filter(item => String(item.settleStatus) === String(query.settleStatus))
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}

// ------------------------------------------------------------
// 二、应付账款
// 真实接口：
// export function listPayable(query) {
//   return request({ url: '/erp/finance/payable/list', method: 'get', params: query })
// }
let payableDb = [
  { payableId: 1, payableNo: 'AP20260818001', supplierName: '华宇金属材料有限公司', billNo: 'PO20260818001', billDate: '2026-08-10', amount: 242500, paidAmount: 242500, unpaidAmount: 0, settleStatus: '2', dueDate: '2026-09-09', remark: '已结清' },
  { payableId: 2, payableNo: 'AP20260818002', supplierName: '深圳联创电子有限公司', billNo: 'PO20260818002', billDate: '2026-08-12', amount: 42500, paidAmount: 20000, unpaidAmount: 22500, settleStatus: '1', dueDate: '2026-09-11', remark: '部分付款' },
  { payableId: 3, payableNo: 'AP20260818003', supplierName: '上海启明包装有限公司', billNo: 'PO20260818003', billDate: '2026-08-15', amount: 17500, paidAmount: 0, unpaidAmount: 17500, settleStatus: '0', dueDate: '2026-09-14', remark: '' }
]
export function listPayable(query) {
  let list = payableDb.slice()
  if (query && query.payableNo) {
    list = list.filter(item => item.payableNo.indexOf(query.payableNo) !== -1)
  }
  if (query && query.supplierName) {
    list = list.filter(item => item.supplierName.indexOf(query.supplierName) !== -1)
  }
  if (query && query.settleStatus !== undefined && query.settleStatus !== '') {
    list = list.filter(item => String(item.settleStatus) === String(query.settleStatus))
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}

// ------------------------------------------------------------
// 三、收付款记录
// 真实接口：
// export function listPayment(query) {
//   return request({ url: '/erp/finance/payment/list', method: 'get', params: query })
// }
// export function addPayment(data) {
//   return request({ url: '/erp/finance/payment', method: 'post', data: data })
// }
let paymentDb = [
  { paymentId: 1, paymentNo: 'PAY20260818001', paymentType: '1', partnerName: '华东机械制造有限公司', billNo: 'AR20260818001', paymentDate: '2026-08-11', amount: 96000, method: '银行转账', status: '2', operator: '财务部-张会计', remark: '销售首款' },
  { paymentId: 2, paymentNo: 'PAY20260818002', paymentType: '2', partnerName: '华宇金属材料有限公司', billNo: 'AP20260818001', paymentDate: '2026-08-13', amount: 242500, method: '银行转账', status: '2', operator: '财务部-张会计', remark: '采购结算' },
  { paymentId: 3, paymentNo: 'PAY20260818003', paymentType: '2', partnerName: '深圳联创电子有限公司', billNo: 'AP20260818002', paymentDate: '2026-08-14', amount: 20000, method: '银行转账', status: '1', operator: '财务部-张会计', remark: '采购部分付款' },
  { paymentId: 4, paymentNo: 'PAY20260818004', paymentType: '1', partnerName: '北方重工集团', billNo: 'AR20260818003', paymentDate: '2026-08-16', amount: 76000, method: '银行承兑汇票', status: '1', operator: '财务部-张会计', remark: '销售尾款' }
]
export function listPayment(query) {
  let list = paymentDb.slice()
  if (query && query.paymentNo) {
    list = list.filter(item => item.paymentNo.indexOf(query.paymentNo) !== -1)
  }
  if (query && query.paymentType) {
    list = list.filter(item => item.paymentType === query.paymentType)
  }
  if (query && query.partnerName) {
    list = list.filter(item => item.partnerName.indexOf(query.partnerName) !== -1)
  }
  const total = list.length
  const pageNum = (query && query.pageNum) || 1
  const pageSize = (query && query.pageSize) || 10
  const rows = list.slice((pageNum - 1) * pageSize, pageNum * pageSize)
  return Promise.resolve({ rows, total })
}
export function addPayment(data) {
  data.paymentId = paymentDb.length ? Math.max(...paymentDb.map(item => item.paymentId)) + 1 : 1
  data.paymentNo = 'PAY20260818' + String(data.paymentId).padStart(3, '0')
  paymentDb.unshift(data)
  return Promise.resolve({})
}

// 通用工具：格式化当前时间
function formatNow() {
  const d = new Date()
  const pad = n => (n < 10 ? '0' + n : '' + n)
  return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds())
}