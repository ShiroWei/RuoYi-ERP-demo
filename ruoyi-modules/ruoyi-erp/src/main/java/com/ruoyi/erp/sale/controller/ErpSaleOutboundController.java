package com.ruoyi.erp.sale.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.erp.sale.domain.ErpSaleOutbound;
import com.ruoyi.erp.sale.service.IErpSaleOutboundService;

/**
 * 销售出库单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/sale/outbound")
public class ErpSaleOutboundController extends BaseController
{
    @Autowired
    private IErpSaleOutboundService saleOutboundService;

    /**
     * 查询销售出库单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpSaleOutbound ErpSaleOutbound)
    {
        startPage();
        List<ErpSaleOutbound> list = saleOutboundService.selectErpSaleOutboundList(ErpSaleOutbound);
        return getDataTable(list);
    }

    /**
     * 获取销售出库单详细信息
     */
    @GetMapping(value = "/{outboundId}")
    public AjaxResult getInfo(@PathVariable("outboundId") Long outboundId)
    {
        return success(saleOutboundService.selectErpSaleOutboundById(outboundId));
    }

    /**
     * 新增销售出库单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpSaleOutbound ErpSaleOutbound)
    {
        return toAjax(saleOutboundService.insertErpSaleOutbound(ErpSaleOutbound));
    }

    /**
     * 修改销售出库单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpSaleOutbound ErpSaleOutbound)
    {
        return toAjax(saleOutboundService.updateErpSaleOutbound(ErpSaleOutbound));
    }

    /**
     * 删除销售出库单
     */
    @DeleteMapping("/{outboundId}")
    public AjaxResult remove(@PathVariable Long outboundId)
    {
        return toAjax(saleOutboundService.deleteErpSaleOutboundById(outboundId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long outboundId)
    {
        return toAjax(saleOutboundService.submitErpSaleOutbound(outboundId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long outboundId)
    {
        return toAjax(saleOutboundService.approveErpSaleOutbound(outboundId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long outboundId)
    {
        return toAjax(saleOutboundService.rejectErpSaleOutbound(outboundId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long outboundId)
    {
        return toAjax(saleOutboundService.completeErpSaleOutbound(outboundId));
    }
}