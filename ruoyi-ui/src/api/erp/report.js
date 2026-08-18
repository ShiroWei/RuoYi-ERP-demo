import request from '@/utils/request'

// ============================================================
// ERP 报表中心 API
// 当前全部为 mock 演示数据（内存数组，支撑报表中心图表与明细）；
// 接入真实后端后，取消注释下方 request 调用并删除 mock 函数即可。
// 真实接口示例：
// export function getPurchaseReportData(query) {
//   return request({ url: '/erp/report/purchase', method: 'get', params: query })
// }
// ============================================================

// 一、采购报表
export function getPurchaseReportData(query) {
  return Promise.resolve({
    summary: [
      { label: '采购订单总额(元)', value: 398500 },
      { label: '本月采购笔数', value: 36 },
      { label: '供应商数量', value: 12 },
      { label: '平均采购周期(天)', value: 3.2 }
    ],
    trend: {
      dates: ['08-13', '08-14', '08-15', '08-16', '08-17', '08-18', '08-19'],
      amounts: [28500, 42000, 38500, 56000, 48200, 67500, 84200]
    },
    supplierPie: [
      { name: '华宇金属材料', value: 242500 },
      { name: '深圳联创电子', value: 42500 },
      { name: '上海启明包装', value: 17500 },
      { name: '其他供应商', value: 96000 }
    ],
    rows: [
      { no: 'PO20260818001', supplierName: '华宇金属材料有限公司', materialName: '原材料-钢板', amount: 242500, orderDate: '2026-08-10', status: '已完成' },
      { no: 'PO20260818002', supplierName: '深圳联创电子有限公司', materialName: '电子元器件', amount: 42500, orderDate: '2026-08-12', status: '审核通过' },
      { no: 'PO20260818003', supplierName: '上海启明包装有限公司', materialName: '包装纸箱', amount: 17500, orderDate: '2026-08-15', status: '待审核' },
      { no: 'PO20260818004', supplierName: '华宇金属材料有限公司', materialName: '半成品-电机组件', amount: 96000, orderDate: '2026-08-17', status: '草稿' }
    ]
  })
}

// 二、销售报表
export function getSaleReportData(query) {
  return Promise.resolve({
    summary: [
      { label: '销售订单总额(元)', value: 352000 },
      { label: '本月销售笔数', value: 28 },
      { label: '客户数量', value: 9 },
      { label: '平均回款天数(天)', value: 28 }
    ],
    trend: {
      dates: ['08-13', '08-14', '08-15', '08-16', '08-17', '08-18', '08-19'],
      amounts: [36000, 45200, 39800, 62000, 54800, 68500, 45700]
    },
    productRank: [
      { name: '成品-产品A', value: 260000 },
      { name: '成品-产品B', value: 56000 },
      { name: '半成品-电机组件', value: 22000 },
      { name: '其他', value: 14000 }
    ],
    rows: [
      { no: 'SO20260818001', customerName: '华东机械制造有限公司', productName: '成品-产品A', amount: 160000, orderDate: '2026-08-11', status: '已完成' },
      { no: 'SO20260818002', customerName: '华南电子科技有限公司', productName: '成品-产品B', amount: 84000, orderDate: '2026-08-13', status: '审核通过' },
      { no: 'SO20260818003', customerName: '北方重工集团', productName: '成品-产品A', amount: 76000, orderDate: '2026-08-15', status: '待审核' },
      { no: 'SO20260818004', customerName: '华东机械制造有限公司', productName: '半成品-电机组件', amount: 32000, orderDate: '2026-08-17', status: '草稿' }
    ]
  })
}

// 三、库存报表
export function getStockReportData(query) {
  return Promise.resolve({
    summary: [
      { label: '库存总量(件)', value: 128650 },
      { label: '库存金额(元)', value: 892000 },
      { label: '预警物料数', value: 8 },
      { label: '仓库数量', value: 4 }
    ],
    stockRank: [
      { name: '电子元器件', value: 12000 },
      { name: '成品-产品A', value: 520 },
      { name: '原材料-钢板', value: 350 },
      { name: '半成品-电机组件', value: 280 },
      { name: '成品-产品B', value: 120 }
    ],
    warehousePie: [
      { name: '总仓-原材料仓', value: 42000 },
      { name: '半成品仓', value: 18500 },
      { name: '成品仓', value: 24150 },
      { name: '华东周转仓', value: 12000 }
    ],
    inOutTrend: {
      dates: ['08-13', '08-14', '08-15', '08-16', '08-17', '08-18', '08-19'],
      in: [320, 480, 560, 420, 610, 540, 720],
      out: [280, 520, 430, 610, 380, 590, 460]
    },
    rows: [
      { code: 'M1001', name: '原材料-钢板', warehouse: '总仓-原材料仓', stock: 350, safeStock: 500, unit: '吨', status: '不足' },
      { code: 'M1002', name: '电子元器件', warehouse: '总仓-原材料仓', stock: 12000, safeStock: 2000, unit: '个', status: '正常' },
      { code: 'M2001', name: '半成品-电机组件', warehouse: '半成品仓', stock: 280, safeStock: 200, unit: '台', status: '正常' },
      { code: 'M3001', name: '成品-产品A', warehouse: '成品仓', stock: 520, safeStock: 100, unit: '台', status: '正常' },
      { code: 'M3002', name: '成品-产品B', warehouse: '成品仓', stock: 120, safeStock: 80, unit: '台', status: '正常' },
      { code: 'M4001', name: '包装纸箱', warehouse: '总仓-原材料仓', stock: 2600, safeStock: 5000, unit: '个', status: '不足' }
    ]
  })
}

// 四、利润报表
export function getProfitReportData(query) {
  return Promise.resolve({
    summary: [
      { label: '本月营收(元)', value: 352000 },
      { label: '本月成本(元)', value: 241600 },
      { label: '本月毛利(元)', value: 110400 },
      { label: '综合毛利率', value: '31.4%' }
    ],
    trend: {
      dates: ['01月', '02月', '03月', '04月', '05月', '06月', '07月', '08月'],
      revenue: [210, 258, 302, 286, 342, 318, 365, 352],
      cost: [152, 184, 208, 198, 238, 226, 252, 242],
      profit: [58, 74, 94, 88, 104, 92, 113, 110]
    },
    productProfit: [
      { name: '成品-产品A', value: 32.6 },
      { name: '成品-产品B', value: 27.3 },
      { name: '半成品-电机组件', value: 18.8 },
      { name: '其他', value: 12.4 }
    ],
    costPie: [
      { name: '原材料成本', value: 169200 },
      { name: '人工成本', value: 42300 },
      { name: '制造费用', value: 18100 },
      { name: '销售费用', value: 12000 }
    ],
    rows: [
      { no: 'SO20260818001', customerName: '华东机械制造有限公司', productName: '成品-产品A', revenue: 160000, cost: 120000, profit: 40000, profitRate: '25.0%' },
      { no: 'SO20260818002', customerName: '华南电子科技有限公司', productName: '成品-产品B', revenue: 84000, cost: 66000, profit: 18000, profitRate: '21.4%' },
      { no: 'SO20260818003', customerName: '北方重工集团', productName: '成品-产品A', revenue: 76000, cost: 57000, profit: 19000, profitRate: '25.0%' },
      { no: 'SO20260818004', customerName: '华东机械制造有限公司', productName: '半成品-电机组件', revenue: 32000, cost: 26000, profit: 6000, profitRate: '18.8%' }
    ]
  })
}