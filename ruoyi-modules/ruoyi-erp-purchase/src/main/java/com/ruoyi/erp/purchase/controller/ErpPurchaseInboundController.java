package com.ruoyi.erp.purchase.controller;

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
import com.ruoyi.erp.purchase.domain.ErpPurchaseInbound;
import com.ruoyi.erp.purchase.service.IErpPurchaseInboundService;

/**
 * 采购入库单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/purchase/inbound")
public class ErpPurchaseInboundController extends BaseController
{
    @Autowired
    private IErpPurchaseInboundService purchaseInboundService;

    /**
     * 查询采购入库单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpPurchaseInbound erpPurchaseInbound)
    {
        startPage();
        List<ErpPurchaseInbound> list = purchaseInboundService.selectErpPurchaseInboundList(erpPurchaseInbound);
        return getDataTable(list);
    }

    /**
     * 获取采购入库单详细信息
     */
    @GetMapping(value = "/{inboundId}")
    public AjaxResult getInfo(@PathVariable("inboundId") Long inboundId)
    {
        return success(purchaseInboundService.selectErpPurchaseInboundById(inboundId));
    }

    /**
     * 新增采购入库单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpPurchaseInbound erpPurchaseInbound)
    {
        return toAjax(purchaseInboundService.insertErpPurchaseInbound(erpPurchaseInbound));
    }

    /**
     * 修改采购入库单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpPurchaseInbound erpPurchaseInbound)
    {
        return toAjax(purchaseInboundService.updateErpPurchaseInbound(erpPurchaseInbound));
    }

    /**
     * 删除采购入库单
     */
    @DeleteMapping("/{inboundId}")
    public AjaxResult remove(@PathVariable Long inboundId)
    {
        return toAjax(purchaseInboundService.deleteErpPurchaseInboundById(inboundId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long inboundId)
    {
        return toAjax(purchaseInboundService.submitErpPurchaseInbound(inboundId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long inboundId)
    {
        return toAjax(purchaseInboundService.approveErpPurchaseInbound(inboundId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long inboundId)
    {
        return toAjax(purchaseInboundService.rejectErpPurchaseInbound(inboundId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long inboundId)
    {
        return toAjax(purchaseInboundService.completeErpPurchaseInbound(inboundId));
    }
}