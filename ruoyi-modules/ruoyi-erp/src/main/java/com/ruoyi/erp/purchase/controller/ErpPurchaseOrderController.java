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
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrder;
import com.ruoyi.erp.purchase.service.IErpPurchaseOrderService;

/**
 * 采购订单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/purchase/order")
public class ErpPurchaseOrderController extends BaseController
{
    @Autowired
    private IErpPurchaseOrderService purchaseOrderService;

    /**
     * 查询采购订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpPurchaseOrder erpPurchaseOrder)
    {
        startPage();
        List<ErpPurchaseOrder> list = purchaseOrderService.selectErpPurchaseOrderList(erpPurchaseOrder);
        return getDataTable(list);
    }

    /**
     * 获取采购订单详细信息
     */
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(purchaseOrderService.selectErpPurchaseOrderById(orderId));
    }

    /**
     * 新增采购订单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpPurchaseOrder erpPurchaseOrder)
    {
        return toAjax(purchaseOrderService.insertErpPurchaseOrder(erpPurchaseOrder));
    }

    /**
     * 修改采购订单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpPurchaseOrder erpPurchaseOrder)
    {
        return toAjax(purchaseOrderService.updateErpPurchaseOrder(erpPurchaseOrder));
    }

    /**
     * 删除采购订单
     */
    @DeleteMapping("/{orderId}")
    public AjaxResult remove(@PathVariable Long orderId)
    {
        return toAjax(purchaseOrderService.deleteErpPurchaseOrderById(orderId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long orderId)
    {
        return toAjax(purchaseOrderService.submitErpPurchaseOrder(orderId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long orderId)
    {
        return toAjax(purchaseOrderService.approveErpPurchaseOrder(orderId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long orderId)
    {
        return toAjax(purchaseOrderService.rejectErpPurchaseOrder(orderId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long orderId)
    {
        return toAjax(purchaseOrderService.completeErpPurchaseOrder(orderId));
    }
}