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
import com.ruoyi.erp.stock.domain.ErpStockRecord;
import com.ruoyi.erp.stock.service.IErpStockRecordService;

/**
 * 出入库记录 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/stock/record")
public class ErpStockRecordController extends BaseController
{
    @Autowired
    private IErpStockRecordService stockRecordService;

    /**
     * 查询出入库记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpStockRecord erpStockRecord)
    {
        startPage();
        List<ErpStockRecord> list = stockRecordService.selectErpStockRecordList(erpStockRecord);
        return getDataTable(list);
    }

    /**
     * 获取出入库记录详细信息
     */
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(stockRecordService.selectErpStockRecordById(recordId));
    }

    /**
     * 新增出入库记录
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpStockRecord erpStockRecord)
    {
        return toAjax(stockRecordService.insertErpStockRecord(erpStockRecord));
    }

    /**
     * 修改出入库记录
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpStockRecord erpStockRecord)
    {
        return toAjax(stockRecordService.updateErpStockRecord(erpStockRecord));
    }

    /**
     * 删除出入库记录
     */
    @DeleteMapping("/{recordId}")
    public AjaxResult remove(@PathVariable Long recordId)
    {
        return toAjax(stockRecordService.deleteErpStockRecordById(recordId));
    }
}