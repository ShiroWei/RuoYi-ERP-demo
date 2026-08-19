package com.ruoyi.erp.report.mapper;

import java.util.List;
import java.util.Map;

/**
 * 报表统计Mapper接口
 * 
 * @author erp
 */
public interface ErpReportMapper
{
    /**
     * 采购报表-汇总
     */
    public List<Map<String, Object>> selectPurchaseSummary();

    /**
     * 采购报表-近7日趋势
     */
    public List<Map<String, Object>> selectPurchaseTrend();

    /**
     * 采购报表-供应商分布
     */
    public List<Map<String, Object>> selectPurchaseBySupplier();

    /**
     * 采购报表-明细行
     */
    public List<Map<String, Object>> selectPurchaseRows();

    /**
     * 销售报表-汇总
     */
    public List<Map<String, Object>> selectSaleSummary();

    /**
     * 销售报表-近7日趋势
     */
    public List<Map<String, Object>> selectSaleTrend();

    /**
     * 销售报表-商品排名
     */
    public List<Map<String, Object>> selectSaleByProduct();

    /**
     * 销售报表-明细行
     */
    public List<Map<String, Object>> selectSaleRows();

    /**
     * 库存报表-汇总
     */
    public List<Map<String, Object>> selectStockSummary();

    /**
     * 库存报表-库存排行
     */
    public List<Map<String, Object>> selectStockRank();

    /**
     * 库存报表-仓库分布
     */
    public List<Map<String, Object>> selectStockByWarehouse();

    /**
     * 库存报表-近7日出入库趋势
     */
    public List<Map<String, Object>> selectInOutTrend();

    /**
     * 库存报表-明细行
     */
    public List<Map<String, Object>> selectStockRows();

    /**
     * 利润报表-营收
     */
    public List<Map<String, Object>> selectProfitRevenue();

    /**
     * 利润报表-成本
     */
    public List<Map<String, Object>> selectProfitCost();

    /**
     * 利润报表-月度趋势
     */
    public List<Map<String, Object>> selectProfitTrend();

    /**
     * 利润报表-商品利润
     */
    public List<Map<String, Object>> selectProfitByProduct();

    /**
     * 利润报表-成本构成
     */
    public List<Map<String, Object>> selectProfitCostPie();

    /**
     * 利润报表-明细行
     */
    public List<Map<String, Object>> selectProfitRows();

    /**
     * 待办统计-各模块待审核数量
     */
    public List<Map<String, Object>> selectPendingCounts();
}