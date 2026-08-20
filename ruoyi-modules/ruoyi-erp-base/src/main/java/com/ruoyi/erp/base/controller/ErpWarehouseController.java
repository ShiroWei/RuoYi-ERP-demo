package com.ruoyi.erp.base.controller;

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
import com.ruoyi.erp.base.domain.ErpWarehouse;
import com.ruoyi.erp.base.service.IErpWarehouseService;

/**
 * 仓库档案 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/warehouse")
public class ErpWarehouseController extends BaseController
{
    @Autowired
    private IErpWarehouseService warehouseService;

    /**
     * 查询仓库档案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpWarehouse erpWarehouse)
    {
        startPage();
        List<ErpWarehouse> list = warehouseService.selectErpWarehouseList(erpWarehouse);
        return getDataTable(list);
    }

    /**
     * 获取仓库档案详细信息
     */
    @GetMapping(value = "/{warehouseId}")
    public AjaxResult getInfo(@PathVariable("warehouseId") Long warehouseId)
    {
        return success(warehouseService.selectErpWarehouseById(warehouseId));
    }

    /**
     * 新增仓库档案
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpWarehouse erpWarehouse)
    {
        return toAjax(warehouseService.insertErpWarehouse(erpWarehouse));
    }

    /**
     * 修改仓库档案
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpWarehouse erpWarehouse)
    {
        return toAjax(warehouseService.updateErpWarehouse(erpWarehouse));
    }

    /**
     * 删除仓库档案
     */
    @DeleteMapping("/{warehouseId}")
    public AjaxResult remove(@PathVariable Long warehouseId)
    {
        return toAjax(warehouseService.deleteErpWarehouseById(warehouseId));
    }
}