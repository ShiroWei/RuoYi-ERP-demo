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
import com.ruoyi.erp.stock.domain.ErpStockCheck;
import com.ruoyi.erp.stock.service.IErpStockCheckService;

/**
 * 库存盘点 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/stock/check")
public class ErpStockCheckController extends BaseController
{
    @Autowired
    private IErpStockCheckService stockCheckService;

    /**
     * 查询库存盘点列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpStockCheck erpStockCheck)
    {
        startPage();
        List<ErpStockCheck> list = stockCheckService.selectErpStockCheckList(erpStockCheck);
        return getDataTable(list);
    }

    /**
     * 获取库存盘点详细信息
     */
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return success(stockCheckService.selectErpStockCheckById(checkId));
    }

    /**
     * 新增库存盘点
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpStockCheck erpStockCheck)
    {
        return toAjax(stockCheckService.insertErpStockCheck(erpStockCheck));
    }

    /**
     * 修改库存盘点
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpStockCheck erpStockCheck)
    {
        return toAjax(stockCheckService.updateErpStockCheck(erpStockCheck));
    }

    /**
     * 删除库存盘点
     */
    @DeleteMapping("/{checkId}")
    public AjaxResult remove(@PathVariable Long checkId)
    {
        return toAjax(stockCheckService.deleteErpStockCheckById(checkId));
    }
}