package com.ruoyi.erp.report.service;

import java.util.Map;

/**
 * 报表统计Service接口
 * 
 * @author erp
 */
public interface IErpReportService
{
    /**
     * 采购报表
     */
    public Map<String, Object> getPurchaseReport();

    /**
     * 销售报表
     */
    public Map<String, Object> getSaleReport();

    /**
     * 库存报表
     */
    public Map<String, Object> getStockReport();

    /**
     * 利润报表
     */
    public Map<String, Object> getProfitReport();

    /**
     * 待办统计
     */
    public Map<String, Object> getPendingCounts();
}