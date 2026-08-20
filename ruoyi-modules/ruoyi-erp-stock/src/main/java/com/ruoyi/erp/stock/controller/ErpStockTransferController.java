package com.ruoyi.erp.stock.controller;

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
import com.ruoyi.erp.stock.domain.ErpStockTransfer;
import com.ruoyi.erp.stock.service.IErpStockTransferService;

/**
 * 库存调拨 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/stock/transfer")
public class ErpStockTransferController extends BaseController
{
    @Autowired
    private IErpStockTransferService stockTransferService;

    /**
     * 查询库存调拨列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpStockTransfer erpStockTransfer)
    {
        startPage();
        List<ErpStockTransfer> list = stockTransferService.selectErpStockTransferList(erpStockTransfer);
        return getDataTable(list);
    }

    /**
     * 获取库存调拨详细信息
     */
    @GetMapping(value = "/{transferId}")
    public AjaxResult getInfo(@PathVariable("transferId") Long transferId)
    {
        return success(stockTransferService.selectErpStockTransferById(transferId));
    }

    /**
     * 新增库存调拨
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpStockTransfer erpStockTransfer)
    {
        return toAjax(stockTransferService.insertErpStockTransfer(erpStockTransfer));
    }

    /**
     * 修改库存调拨
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpStockTransfer erpStockTransfer)
    {
        return toAjax(stockTransferService.updateErpStockTransfer(erpStockTransfer));
    }

    /**
     * 删除库存调拨
     */
    @DeleteMapping("/{transferId}")
    public AjaxResult remove(@PathVariable Long transferId)
    {
        return toAjax(stockTransferService.deleteErpStockTransferById(transferId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long transferId)
    {
        return toAjax(stockTransferService.submitErpStockTransfer(transferId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long transferId)
    {
        return toAjax(stockTransferService.approveErpStockTransfer(transferId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long transferId)
    {
        return toAjax(stockTransferService.rejectErpStockTransfer(transferId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long transferId)
    {
        return toAjax(stockTransferService.completeErpStockTransfer(transferId));
    }
}