package com.ruoyi.erp.report.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.report.mapper.ErpReportMapper;
import com.ruoyi.erp.report.service.IErpReportService;

/**
 * 报表统计Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpReportServiceImpl implements IErpReportService
{
    @Autowired
    private ErpReportMapper reportMapper;

    /**
     * 采购报表
     */
    @Override
    public Map<String, Object> getPurchaseReport()
    {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> summaryList = reportMapper.selectPurchaseSummary();
        Map<String, Object> s = summaryList.isEmpty() ? new HashMap<>() : summaryList.get(0);
        List<Map<String, Object>> summary = new ArrayList<>();
        summary.add(card("采购订单总额(元)", toBigDecimal(s.get("totalAmount")).longValue()));
        summary.add(card("本月采购订单", toBigDecimal(s.get("orderCount")).longValue()));
        summary.add(card("供应商数量", toBigDecimal(s.get("supplierCount")).longValue()));
        summary.add(card("平均采购金额(元)", toBigDecimal(s.get("avgAmount")).longValue()));
        result.put("summary", summary);
        result.put("trend", fillTrend(reportMapper.selectPurchaseTrend(), "amount", "date"));
        result.put("supplierPie", reportMapper.selectPurchaseBySupplier());
        result.put("rows", translateRows(reportMapper.selectPurchaseRows(), "status"));
        return result;
    }

    /**
     * 销售报表
     */
    @Override
    public Map<String, Object> getSaleReport()
    {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> summaryList = reportMapper.selectSaleSummary();
        Map<String, Object> s = summaryList.isEmpty() ? new HashMap<>() : summaryList.get(0);
        List<Map<String, Object>> summary = new ArrayList<>();
        summary.add(card("销售订单总额(元)", toBigDecimal(s.get("totalAmount")).longValue()));
        summary.add(card("本月销售订单", toBigDecimal(s.get("orderCount")).longValue()));
        summary.add(card("客户数量", toBigDecimal(s.get("customerCount")).longValue()));
        summary.add(card("平均订单金额(元)", toBigDecimal(s.get("avgAmount")).longValue()));
        result.put("summary", summary);
        result.put("trend", fillTrend(reportMapper.selectSaleTrend(), "amount", "date"));
        result.put("productRank", reportMapper.selectSaleByProduct());
        result.put("rows", translateRows(reportMapper.selectSaleRows(), "status"));
        return result;
    }

    /**
     * 库存报表
     */
    @Override
    public Map<String, Object> getStockReport()
    {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> summaryList = reportMapper.selectStockSummary();
        Map<String, Object> s = summaryList.isEmpty() ? new HashMap<>() : summaryList.get(0);
        List<Map<String, Object>> summary = new ArrayList<>();
        summary.add(card("库存总量(件)", toBigDecimal(s.get("totalQty")).longValue()));
        summary.add(card("库存金额(元)", toBigDecimal(s.get("totalValue")).longValue()));
        summary.add(card("预警物料数", toBigDecimal(s.get("lowCount")).longValue()));
        summary.add(card("仓库数量", toBigDecimal(s.get("warehouseCount")).longValue()));
        result.put("summary", summary);
        result.put("stockRank", reportMapper.selectStockRank());
        result.put("warehousePie", reportMapper.selectStockByWarehouse());
        result.put("inOutTrend", fillInOutTrend(reportMapper.selectInOutTrend()));
        result.put("rows", reportMapper.selectStockRows());
        return result;
    }

    /**
     * 利润报表
     */
    @Override
    public Map<String, Object> getProfitReport()
    {
        Map<String, Object> result = new HashMap<>();
        BigDecimal revenue = reportMapper.selectProfitRevenue().isEmpty()
                ? BigDecimal.ZERO : toBigDecimal(reportMapper.selectProfitRevenue().get(0).get("revenue"));
        BigDecimal cost = reportMapper.selectProfitCost().isEmpty()
                ? BigDecimal.ZERO : toBigDecimal(reportMapper.selectProfitCost().get(0).get("cost"));
        BigDecimal profit = revenue.subtract(cost);
        BigDecimal profitRate = revenue.signum() == 0 ? BigDecimal.ZERO
                : profit.divide(revenue, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
        List<Map<String, Object>> summary = new ArrayList<>();
        summary.add(card("销售营收(元)", revenue.longValue()));
        summary.add(card("销售成本(元)", cost.longValue()));
        summary.add(card("销售毛利(元)", profit.longValue()));
        summary.add(card("综合毛利率", profitRate.stripTrailingZeros().toPlainString() + "%"));
        result.put("summary", summary);

        List<Map<String, Object>> trendList = reportMapper.selectProfitTrend();
        List<String> months = new ArrayList<>();
        List<BigDecimal> revenues = new ArrayList<>();
        List<BigDecimal> costs = new ArrayList<>();
        List<BigDecimal> profits = new ArrayList<>();
        for (Map<String, Object> row : trendList)
        {
            BigDecimal r = toBigDecimal(row.get("revenue"));
            BigDecimal c = toBigDecimal(row.get("cost"));
            months.add(String.valueOf(row.get("month")));
            revenues.add(r);
            costs.add(c);
            profits.add(r.subtract(c));
        }
        Map<String, Object> trend = new HashMap<>();
        trend.put("dates", months);
        trend.put("revenue", revenues);
        trend.put("cost", costs);
        trend.put("profit", profits);
        result.put("trend", trend);
        result.put("productProfit", reportMapper.selectProfitByProduct());
        result.put("rows", translateProfitRows(reportMapper.selectProfitRows()));
        return result;
    }

    /**
     * 待办统计
     */
    @Override
    public Map<String, Object> getPendingCounts()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("items", reportMapper.selectPendingCounts());
        return result;
    }

    /**
     * 卡片数据
     */
    private Map<String, Object> card(String label, Object value)
    {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("label", label);
        item.put("value", value);
        return item;
    }

    /**
     * 补齐近7日趋势中缺失的日期
     */
    private Map<String, Object> fillTrend(List<Map<String, Object>> rows, String valueKey, String dateKey)
    {
        Map<String, BigDecimal> data = new HashMap<>();
        for (Map<String, Object> row : rows)
        {
            data.put(String.valueOf(row.get(dateKey)), toBigDecimal(row.get(valueKey)));
        }
        List<String> dates = new ArrayList<>();
        List<BigDecimal> amounts = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        for (int i = 0; i < 7; i++)
        {
            String key = fmt.format(cal.getTime());
            dates.add(key);
            BigDecimal v = data.get(key);
            amounts.add(v == null ? BigDecimal.ZERO : v);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Map<String, Object> trend = new HashMap<>();
        trend.put("dates", dates);
        trend.put("amounts", amounts);
        return trend;
    }

    /**
     * 补齐近7日出入库趋势
     */
    private Map<String, Object> fillInOutTrend(List<Map<String, Object>> rows)
    {
        Map<String, BigDecimal[]> data = new HashMap<>();
        for (Map<String, Object> row : rows)
        {
            data.put(String.valueOf(row.get("date")),
                    new BigDecimal[] { toBigDecimal(row.get("inQty")), toBigDecimal(row.get("outQty")) });
        }
        List<String> dates = new ArrayList<>();
        List<BigDecimal> inList = new ArrayList<>();
        List<BigDecimal> outList = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        for (int i = 0; i < 7; i++)
        {
            String key = fmt.format(cal.getTime());
            dates.add(key);
            BigDecimal[] v = data.get(key);
            inList.add(v == null ? BigDecimal.ZERO : v[0]);
            outList.add(v == null ? BigDecimal.ZERO : v[1]);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Map<String, Object> trend = new HashMap<>();
        trend.put("dates", dates);
        trend.put("in", inList);
        trend.put("out", outList);
        return trend;
    }

    /**
     * 单据状态翻译
     */
    private List<Map<String, Object>> translateRows(List<Map<String, Object>> rows, String statusKey)
    {
        for (Map<String, Object> row : rows)
        {
            Object status = row.get(statusKey);
            if (status != null)
            {
                row.put(statusKey, statusName(String.valueOf(status)));
            }
        }
        return rows;
    }

    /**
     * 利润明细行：计算利润与利润率
     */
    private List<Map<String, Object>> translateProfitRows(List<Map<String, Object>> rows)
    {
        for (Map<String, Object> row : rows)
        {
            BigDecimal r = toBigDecimal(row.get("revenue"));
            BigDecimal c = toBigDecimal(row.get("cost"));
            BigDecimal p = r.subtract(c);
            BigDecimal rate = r.signum() == 0 ? BigDecimal.ZERO
                    : p.divide(r, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
            row.put("profit", p);
            row.put("profitRate", rate.stripTrailingZeros().toPlainString() + "%");
        }
        return rows;
    }

    /**
     * 状态名称
     */
    private String statusName(String status)
    {
        switch (status)
        {
            case "0": return "草稿";
            case "1": return "待审核";
            case "2": return "审核通过";
            case "3": return "已驳回";
            case "4": return "已完成";
            default: return status;
        }
    }

    /**
     * 对象转 BigDecimal
     */
    private BigDecimal toBigDecimal(Object value)
    {
        if (value == null)
        {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(String.valueOf(value));
    }
}