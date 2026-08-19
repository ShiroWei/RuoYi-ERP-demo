import { getPurchaseReportData, getSaleReportData, getStockReportData, getProfitReportData, getPendingCounts } from '@/api/erp/report'
import { listStock } from '@/api/erp/stock'
import { listSupplier, listMaterial } from '@/api/erp/base'

// ============================================================
// AI 助手 · 演示版 Mock 引擎
// 说明：意图识别与话术为前端 Mock（规则匹配，不接入任何真实 AI 服务），
//       回复中的业务数字调用现有真实报表/列表接口填充，使演示更真实可信。
// ============================================================

// 数字格式化：万元 / 千分位
function fmtMoney(val) {
  const n = Number(val || 0)
  if (Math.abs(n) >= 10000) {
    return (n / 10000).toFixed(1).replace(/\.0$/, '') + '万'
  }
  return n.toLocaleString('zh-CN')
}

function fmtNum(val) {
  return Number(val || 0).toLocaleString('zh-CN')
}

// summary 固定顺序取值：purchase/sale[总额,单数,往来户数,均额] stock[总量,总值,预警数,仓数]
function valueOf(summary, index) {
  const item = (summary || [])[index]
  return item ? Number(item.value || 0) : 0
}

// 通用回复对象组装
function reply(payload) {
  return Object.assign({ cards: [], list: [], preview: null, delay: 800 }, payload)
}

// 捕获接口异常，返回兜底回复
function safe(promise, fallback) {
  return promise.catch(() => fallback)
}

// ---------------- 意图处理 ---------------- //

async function intentSale(text) {
  const res = await safe(getSaleReportData(), null)
  if (!res || !res.data) {
    return reply({ reply: '抱歉，销售数据暂时拉取失败，请稍后再试。' })
  }
  const d = res.data
  const total = valueOf(d.summary, 0)
  const orderCount = valueOf(d.summary, 1)
  const customerCount = valueOf(d.summary, 2)
  const avg = valueOf(d.summary, 3)
  const top = (d.productRank || []).slice(0, 3).map(i => `${i.name}(${fmtMoney(i.value)}元)`)
  return reply({
    reply: `当前销售经营情况：销售订单总额 ${fmtMoney(total)}元，共 ${fmtNum(orderCount)} 笔订单，覆盖 ${fmtNum(customerCount)} 家客户，平均单笔 ${fmtMoney(avg)}元。` +
      (top.length ? ` 销售额 TOP：${top.join('、')}。` : '') +
      ' 如需查看明细可前往「报表中心-销售报表」。',
    cards: [
      { label: '销售订单总额', value: fmtMoney(total) + '元' },
      { label: '订单数量', value: fmtNum(orderCount) },
      { label: '客户数量', value: fmtNum(customerCount) },
      { label: '平均订单金额', value: fmtMoney(avg) + '元' }
    ],
    list: (d.productRank || []).slice(0, 5).map((i, idx) => ({ title: `${idx + 1}. ${i.name}`, desc: `销售额 ${fmtMoney(i.value)}元` })),
    delay: 1200
  })
}

async function intentPurchase(text) {
  const res = await safe(getPurchaseReportData(), null)
  if (!res || !res.data) {
    return reply({ reply: '抱歉，采购数据暂时拉取失败，请稍后再试。' })
  }
  const d = res.data
  const total = valueOf(d.summary, 0)
  const orderCount = valueOf(d.summary, 1)
  const supplierCount = valueOf(d.summary, 2)
  const avg = valueOf(d.summary, 3)
  return reply({
    reply: `当前采购情况：采购订单总额 ${fmtMoney(total)}元，共 ${fmtNum(orderCount)} 笔订单，与 ${fmtNum(supplierCount)} 家供应商合作，平均每笔 ${fmtMoney(avg)}元。` +
      ' 如需查看明细可前往「报表中心-采购报表」。',
    cards: [
      { label: '采购订单总额', value: fmtMoney(total) + '元' },
      { label: '订单数量', value: fmtNum(orderCount) },
      { label: '供应商数量', value: fmtNum(supplierCount) },
      { label: '平均采购金额', value: fmtMoney(avg) + '元' }
    ],
    list: (d.supplierPie || []).slice(0, 5).map((i, idx) => ({ title: `${idx + 1}. ${i.name}`, desc: `采购金额 ${fmtMoney(i.value)}元` })),
    delay: 1200
  })
}

async function intentProfit(text) {
  const res = await safe(getProfitReportData(), null)
  if (!res || !res.data) {
    return reply({ reply: '抱歉，利润数据暂时拉取失败，请稍后再试。' })
  }
  const d = res.data
  const revenue = valueOf(d.summary, 0)
  const cost = valueOf(d.summary, 1)
  const profit = valueOf(d.summary, 2)
  const rate = (d.summary || [])[3] ? d.summary[3].value : '0%'
  const trend = d.trend || {}
  const monthName = (trend.dates || []).slice(-1)[0] || ''
  const good = profit >= 0
  return reply({
    reply: `当前经营利润情况${monthName ? '（' + monthName + '）' : ''}：销售营收 ${fmtMoney(revenue)}元，销售成本 ${fmtMoney(cost)}元，毛利 ${fmtMoney(profit)}元，综合毛利率 ${rate}。` +
      (good ? ' 整体盈利状况良好。' : ' 当前处于亏损状态，建议关注成本控制。') +
      ' 如需查看明细可前往「报表中心-利润报表」。',
    cards: [
      { label: '销售营收', value: fmtMoney(revenue) + '元' },
      { label: '销售成本', value: fmtMoney(cost) + '元' },
      { label: '销售毛利', value: fmtMoney(profit) + '元' },
      { label: '综合毛利率', value: rate }
    ],
    list: (d.productProfit || []).slice(0, 5).map((i, idx) => ({ title: `${idx + 1}. ${i.name}`, desc: `毛利 ${fmtMoney(i.value)}元` })),
    delay: 1200
  })
}

async function intentStock(text) {
  const res = await safe(listStock({ pageNum: 1, pageSize: 1000 }), null)
  const report = await safe(getStockReportData(), null)
  const rows = (res && res.rows) || []
  const totalQty = rows.reduce((s, i) => s + Number(i.quantity || 0), 0)
  const totalVal = rows.reduce((s, i) => s + Number(i.amount || 0), 0)
  const warnings = rows.filter(i => Number(i.quantity) < Number(i.safeStock))
  const excess = rows.filter(i => Number(i.quantity) > Number(i.safeStock) * 3)
  let replyText = `当前库存概况：${fmtNum(rows.length)} 个物料维度，库存总量 ${fmtNum(totalQty)}${rows[0] && rows[0].unit ? rows[0].unit : '件'}，库存金额约 ${fmtMoney(totalVal)}元。`
  if (warnings.length) {
    replyText += ` 其中 ${warnings.length} 项低于安全库存，建议尽快补货！`
  } else {
    replyText += ' 各物料库存均在安全水平内。'
  }
  const list = []
  warnings.slice(0, 8).forEach(i => list.push({ title: `${i.materialName}（${i.warehouseName}）`, desc: `当前 ${i.quantity} / 安全库存 ${i.safeStock}`, tag: '不足', tagType: 'danger' }))
  excess.slice(0, 3).forEach(i => list.push({ title: `${i.materialName}（${i.warehouseName}）`, desc: `当前 ${i.quantity} / 安全库存 ${i.safeStock}`, tag: '积压', tagType: 'warning' }))
  if (!list.length) {
    list.push({ title: '库存状态健康', desc: '暂无预警项' })
  }
  return reply({
    reply: replyText,
    cards: [
      { label: '物料维度数', value: fmtNum(rows.length) },
      { label: '库存总量', value: fmtNum(totalQty) },
      { label: '库存金额', value: fmtMoney(totalVal) + '元' },
      { label: '预警物料数', value: fmtNum(warnings.length) }
    ],
    list,
    delay: 1200
  })
}

async function intentPending(text) {
  const res = await safe(getPendingCounts(), null)
  const items = (res && res.data && res.data.items) || []
  const total = items.reduce((s, i) => s + Number(i.count || 0), 0)
  const detail = items.filter(i => Number(i.count) > 0)
  return reply({
    reply: total > 0
      ? `当前共有 ${total} 张单据待审核，明细如下。建议及时处理以免影响业务流转。`
      : '当前没有待审核单据，所有业务都已处理完毕，运营状态良好。',
    cards: [
      { label: '待审单据总数', value: fmtNum(total) }
    ],
    list: detail.length
      ? detail.map(i => ({ title: i.name, desc: `${i.count} 张待审核`, tag: '待审核', tagType: 'warning' }))
      : [{ title: '无待办', desc: '所有单据已审核' }],
    delay: 1000
  })
}

// 生成采购订单草稿（仅预览）
async function intentGenPurchase(text) {
  const suppliers = await safe(listSupplier({ pageNum: 1, pageSize: 1 }), null)
  const materials = await safe(listMaterial({ pageNum: 1, pageSize: 3 }), null)
  const supplier = (suppliers && suppliers.rows && suppliers.rows[0]) || { supplierName: '演示供应商' }
  const rows = (materials && materials.rows) || []
  const items = rows.map((m, i) => ({
    materialId: m.materialId,
    materialCode: m.materialCode,
    materialName: m.materialName,
    specification: m.specification,
    unit: m.unit,
    quantity: 10 * (i + 1),
    price: Number(m.purchasePrice || 0)
  }))
  const total = items.reduce((s, i) => s + i.quantity * i.price, 0)
  const no = 'PO' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + 'AI001'
  const now = new Date()
  const dateStr = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  return reply({
    reply: `已根据需求生成一张采购订单草稿（单号 ${no}），供应商「${supplier.supplierName}」，合计 ${fmtMoney(total)}元。演示模式仅展示预览，未实际提交入库。`,
    preview: {
      type: 'purchase',
      title: `采购订单草稿 · ${no}`,
      fields: [
        { k: '单据编号', v: no },
        { k: '供应商', v: supplier.supplierName },
        { k: '下单日期', v: dateStr },
        { k: '合计金额', v: fmtMoney(total) + '元' },
        { k: '状态', v: '草稿' }
      ],
      items: items.map(i => ({ ...i, amount: (i.quantity * i.price).toFixed(2) }))
    },
    delay: 1400
  })
}

// 生成销售订单草稿（仅预览）
async function intentGenSale(text) {
  const materials = await safe(listMaterial({ pageNum: 1, pageSize: 3 }), null)
  const rows = (materials && materials.rows) || []
  const items = rows.map((m, i) => ({
    materialId: m.materialId,
    materialCode: m.materialCode,
    materialName: m.materialName,
    specification: m.specification,
    unit: m.unit,
    quantity: 5 * (i + 1),
    price: Number(m.salePrice || 0)
  }))
  const total = items.reduce((s, i) => s + i.quantity * i.price, 0)
  const no = 'SO' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + 'AI001'
  const now = new Date()
  const dateStr = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
  return reply({
    reply: `已根据需求生成一张销售订单草稿（单号 ${no}），合计 ${fmtMoney(total)}元。演示模式仅展示预览，未实际提交。`,
    preview: {
      type: 'sale',
      title: `销售订单草稿 · ${no}`,
      fields: [
        { k: '单据编号', v: no },
        { k: '客户', v: '演示客户' },
        { k: '下单日期', v: dateStr },
        { k: '合计金额', v: fmtMoney(total) + '元' },
        { k: '状态', v: '草稿' }
      ],
      items: items.map(i => ({ ...i, amount: (i.quantity * i.price).toFixed(2) }))
    },
    delay: 1400
  })
}

function intentHelp(text) {
  return reply({
    reply: '我是 ERP 智能助手（演示版），可以帮你了解经营情况。你可以问我：\n' +
      '· 销售/采购/利润/库存分析（回复含真实经营数据卡片）\n' +
      '· 库存预警、待审核单据汇总\n' +
      '· 生成采购/销售订单草稿（仅预览）\n' +
      '试试从下方快捷提问开始吧。',
    list: [
      { title: '帮我分析本月销售' },
      { title: '有哪些库存预警？' },
      { title: '汇总一下待审核单据' },
      { title: '生成一张采购订单草稿' },
      { title: '分析一下利润情况' }
    ],
    delay: 700
  })
}

function intentGreet(text) {
  return reply({
    reply: '你好，我是 ERP 智能助手（演示版）！我可以帮你分析经营数据、查看库存预警、汇总待审单据，甚至帮你生成采购/销售订单草稿。有什么可以帮你的吗？',
    delay: 600
  })
}

function intentThanks(text) {
  return reply({
    reply: '不客气！有经营分析或业务问题随时找我，随时为你服务。',
    delay: 500
  })
}

function intentBye(text) {
  return reply({
    reply: '再见！祝您工作顺利，记得关注库存预警和待审单据哦。',
    delay: 500
  })
}

function intentFallback(text) {
  return reply({
    reply: '抱歉，我暂时还没理解这个问题。我是演示版 AI 助手，主要支持经营分析、库存预警、待审汇总与订单草稿生成。试试对我说「帮我分析销售」或「有哪些库存预警」？',
    delay: 700
  })
}

// ---------------- 意图匹配 ---------------- //

// 规则按顺序匹配，命中即返回（Promise）
const rules = [
  { keys: ['你好', '您好', 'hello', 'hi', '在吗', '早上好', '晚上好', '嗨'], fn: intentGreet },
  { keys: ['销售', '卖了多少', '销量', '销售额', '销售情况', '订单金额'], fn: intentSale },
  { keys: ['采购', '进货', '买料', '采购额', '采购情况'], fn: intentPurchase },
  { keys: ['利润', '毛利', '盈利', '赚钱', '收益', '亏', '赚了'], fn: intentProfit },
  { keys: ['库存', '预警', '不足', '缺货', '积压', '存货', '备货'], fn: intentStock },
  { keys: ['待审', '待审核', '审核', '待办', '审批', '待处理'], fn: intentPending },
  { keys: ['生成采购', '建采购', '下采购单', '采购订单'], fn: intentGenPurchase },
  { keys: ['生成销售', '建销售', '下销售单', '销售订单'], fn: intentGenSale },
  { keys: ['帮助', '能做什么', '你会什么', '功能', '你能'], fn: intentHelp },
  { keys: ['谢谢', '感谢', 'thx'], fn: intentThanks },
  { keys: ['再见', '拜拜', 'bye'], fn: intentBye }
]

/**
 * 发送消息给 AI 助手
 * @param {string} text 用户输入
 * @returns {Promise<{reply:string, cards:Array, list:Array, preview:Object, delay:number}>}
 */
export function sendMessage(text) {
  const input = String(text || '').trim().toLowerCase()
  if (!input) {
    return Promise.resolve(intentHelp())
  }
  let hit = null
  for (const rule of rules) {
    if (rule.keys.some(k => input.includes(k))) {
      hit = rule
      break
    }
  }
  const handler = hit ? hit.fn : intentFallback
  return Promise.resolve(handler(input)).catch(() => intentFallback(input))
}

/** 初始欢迎语 */
export function getWelcomeMessage() {
  return {
    reply: '你好，我是 ERP 智能助手（演示版）！我可以帮你分析经营数据、查看库存预警、汇总待审单据，还能生成采购/销售订单草稿（仅预览）。从下方快捷提问开始体验吧。',
    cards: [],
    list: [
      { title: '帮我分析本月销售' },
      { title: '有哪些库存预警？' },
      { title: '汇总一下待审核单据' },
      { title: '生成一张采购订单草稿' }
    ],
    preview: null,
    delay: 600
  }
}
