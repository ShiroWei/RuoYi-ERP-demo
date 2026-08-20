package com.ruoyi.erp.production.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.erp.production.domain.ErpProductionOrder;
import com.ruoyi.erp.production.service.IErpProductionOrderService;

/**
 * 生产工单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/production/order")
public class ErpProductionOrderController extends BaseController
{
    @Autowired
    private IErpProductionOrderService productionOrderService;

    /**
     * 查询生产工单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionOrder erpProductionOrder)
    {
        startPage();
        List<ErpProductionOrder> list = productionOrderService.selectErpProductionOrderList(erpProductionOrder);
        return getDataTable(list);
    }

    /**
     * 获取生产工单详细信息
     */
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(productionOrderService.selectErpProductionOrderById(orderId));
    }

    /**
     * 新增生产工单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionOrder erpProductionOrder)
    {
        return toAjax(productionOrderService.insertErpProductionOrder(erpProductionOrder));
    }

    /**
     * 修改生产工单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionOrder erpProductionOrder)
    {
        return toAjax(productionOrderService.updateErpProductionOrder(erpProductionOrder));
    }

    /**
     * 删除生产工单
     */
    @DeleteMapping("/{orderId}")
    public AjaxResult remove(@PathVariable Long orderId)
    {
        return toAjax(productionOrderService.deleteErpProductionOrderById(orderId));
    }
}