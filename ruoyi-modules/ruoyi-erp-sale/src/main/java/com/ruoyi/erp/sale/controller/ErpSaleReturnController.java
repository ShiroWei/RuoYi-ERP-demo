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
import com.ruoyi.erp.sale.domain.ErpSaleReturn;
import com.ruoyi.erp.sale.service.IErpSaleReturnService;

/**
 * 销售退货单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/sale/return")
public class ErpSaleReturnController extends BaseController
{
    @Autowired
    private IErpSaleReturnService saleReturnService;

    /**
     * 查询销售退货单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpSaleReturn ErpSaleReturn)
    {
        startPage();
        List<ErpSaleReturn> list = saleReturnService.selectErpSaleReturnList(ErpSaleReturn);
        return getDataTable(list);
    }

    /**
     * 获取销售退货单详细信息
     */
    @GetMapping(value = "/{returnId}")
    public AjaxResult getInfo(@PathVariable("returnId") Long returnId)
    {
        return success(saleReturnService.selectErpSaleReturnById(returnId));
    }

    /**
     * 新增销售退货单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpSaleReturn ErpSaleReturn)
    {
        return toAjax(saleReturnService.insertErpSaleReturn(ErpSaleReturn));
    }

    /**
     * 修改销售退货单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpSaleReturn ErpSaleReturn)
    {
        return toAjax(saleReturnService.updateErpSaleReturn(ErpSaleReturn));
    }

    /**
     * 删除销售退货单
     */
    @DeleteMapping("/{returnId}")
    public AjaxResult remove(@PathVariable Long returnId)
    {
        return toAjax(saleReturnService.deleteErpSaleReturnById(returnId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long returnId)
    {
        return toAjax(saleReturnService.submitErpSaleReturn(returnId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long returnId)
    {
        return toAjax(saleReturnService.approveErpSaleReturn(returnId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long returnId)
    {
        return toAjax(saleReturnService.rejectErpSaleReturn(returnId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long returnId)
    {
        return toAjax(saleReturnService.completeErpSaleReturn(returnId));
    }
}