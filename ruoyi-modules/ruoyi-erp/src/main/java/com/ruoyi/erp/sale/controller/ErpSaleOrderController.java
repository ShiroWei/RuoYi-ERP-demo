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
import com.ruoyi.erp.sale.domain.ErpSaleOrder;
import com.ruoyi.erp.sale.service.IErpSaleOrderService;

/**
 * 销售订单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/sale/order")
public class ErpSaleOrderController extends BaseController
{
    @Autowired
    private IErpSaleOrderService saleOrderService;

    /**
     * 查询销售订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpSaleOrder ErpSaleOrder)
    {
        startPage();
        List<ErpSaleOrder> list = saleOrderService.selectErpSaleOrderList(ErpSaleOrder);
        return getDataTable(list);
    }

    /**
     * 获取销售订单详细信息
     */
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(saleOrderService.selectErpSaleOrderById(orderId));
    }

    /**
     * 新增销售订单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpSaleOrder ErpSaleOrder)
    {
        return toAjax(saleOrderService.insertErpSaleOrder(ErpSaleOrder));
    }

    /**
     * 修改销售订单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpSaleOrder ErpSaleOrder)
    {
        return toAjax(saleOrderService.updateErpSaleOrder(ErpSaleOrder));
    }

    /**
     * 删除销售订单
     */
    @DeleteMapping("/{orderId}")
    public AjaxResult remove(@PathVariable Long orderId)
    {
        return toAjax(saleOrderService.deleteErpSaleOrderById(orderId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long orderId)
    {
        return toAjax(saleOrderService.submitErpSaleOrder(orderId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long orderId)
    {
        return toAjax(saleOrderService.approveErpSaleOrder(orderId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long orderId)
    {
        return toAjax(saleOrderService.rejectErpSaleOrder(orderId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long orderId)
    {
        return toAjax(saleOrderService.completeErpSaleOrder(orderId));
    }
}