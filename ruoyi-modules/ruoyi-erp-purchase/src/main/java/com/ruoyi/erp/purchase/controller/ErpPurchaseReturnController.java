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
import com.ruoyi.erp.purchase.domain.ErpPurchaseReturn;
import com.ruoyi.erp.purchase.service.IErpPurchaseReturnService;

/**
 * 采购退货单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/purchase/return")
public class ErpPurchaseReturnController extends BaseController
{
    @Autowired
    private IErpPurchaseReturnService purchaseReturnService;

    /**
     * 查询采购退货单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpPurchaseReturn erpPurchaseReturn)
    {
        startPage();
        List<ErpPurchaseReturn> list = purchaseReturnService.selectErpPurchaseReturnList(erpPurchaseReturn);
        return getDataTable(list);
    }

    /**
     * 获取采购退货单详细信息
     */
    @GetMapping(value = "/{returnId}")
    public AjaxResult getInfo(@PathVariable("returnId") Long returnId)
    {
        return success(purchaseReturnService.selectErpPurchaseReturnById(returnId));
    }

    /**
     * 新增采购退货单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpPurchaseReturn erpPurchaseReturn)
    {
        return toAjax(purchaseReturnService.insertErpPurchaseReturn(erpPurchaseReturn));
    }

    /**
     * 修改采购退货单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpPurchaseReturn erpPurchaseReturn)
    {
        return toAjax(purchaseReturnService.updateErpPurchaseReturn(erpPurchaseReturn));
    }

    /**
     * 删除采购退货单
     */
    @DeleteMapping("/{returnId}")
    public AjaxResult remove(@PathVariable Long returnId)
    {
        return toAjax(purchaseReturnService.deleteErpPurchaseReturnById(returnId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long returnId)
    {
        return toAjax(purchaseReturnService.submitErpPurchaseReturn(returnId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long returnId)
    {
        return toAjax(purchaseReturnService.approveErpPurchaseReturn(returnId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long returnId)
    {
        return toAjax(purchaseReturnService.rejectErpPurchaseReturn(returnId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long returnId)
    {
        return toAjax(purchaseReturnService.completeErpPurchaseReturn(returnId));
    }
}