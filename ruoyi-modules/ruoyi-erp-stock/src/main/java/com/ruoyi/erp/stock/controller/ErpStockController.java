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
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.erp.stock.domain.ErpStock;
import com.ruoyi.erp.stock.service.IErpStockService;

/**
 * 库存 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/stock")
public class ErpStockController extends BaseController
{
    @Autowired
    private IErpStockService stockService;

    /**
     * 查询库存列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpStock erpStock)
    {
        startPage();
        List<ErpStock> list = stockService.selectErpStockList(erpStock);
        return getDataTable(list);
    }

    /**
     * 获取库存详细信息
     */
    @GetMapping(value = "/{stockId}")
    public AjaxResult getInfo(@PathVariable("stockId") Long stockId)
    {
        return success(stockService.selectErpStockById(stockId));
    }

    /**
     * 调整库存数量（供其它服务 Feign 调用）
     */
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody ErpStock erpStock)
    {
        return toAjax(stockService.adjustStock(erpStock));
    }

    /**
     * 新增库存
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpStock erpStock)
    {
        return toAjax(stockService.insertErpStock(erpStock));
    }

    /**
     * 修改库存
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpStock erpStock)
    {
        return toAjax(stockService.updateErpStock(erpStock));
    }

    /**
     * 删除库存
     */
    @DeleteMapping("/{stockId}")
    public AjaxResult remove(@PathVariable Long stockId)
    {
        return toAjax(stockService.deleteErpStockById(stockId));
    }
}