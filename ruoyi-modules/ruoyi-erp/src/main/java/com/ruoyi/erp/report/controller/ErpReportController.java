package com.ruoyi.erp.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.erp.report.service.IErpReportService;

/**
 * 报表统计 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/report")
public class ErpReportController extends BaseController
{
    @Autowired
    private IErpReportService reportService;

    /**
     * 采购报表
     */
    @GetMapping("/purchase")
    public AjaxResult purchase()
    {
        return success(reportService.getPurchaseReport());
    }

    /**
     * 销售报表
     */
    @GetMapping("/sale")
    public AjaxResult sale()
    {
        return success(reportService.getSaleReport());
    }

    /**
     * 库存报表
     */
    @GetMapping("/stock")
    public AjaxResult stock()
    {
        return success(reportService.getStockReport());
    }

    /**
     * 利润报表
     */
    @GetMapping("/profit")
    public AjaxResult profit()
    {
        return success(reportService.getProfitReport());
    }

    /**
     * 待办统计
     */
    @GetMapping("/pending")
    public AjaxResult pending()
    {
        return success(reportService.getPendingCounts());
    }
}