package com.ruoyi.erp.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.erp.report.service.IErpReportService;

/**
 * 工作台待办 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/order")
public class ErpDashboardController extends BaseController
{
    @Autowired
    private IErpReportService reportService;

    /**
     * 待办统计（各模块待审核数量）
     */
    @GetMapping("/pending")
    public AjaxResult pending()
    {
        return success(reportService.getPendingCounts());
    }
}