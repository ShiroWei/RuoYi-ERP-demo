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
import com.ruoyi.erp.base.domain.ErpSupplier;
import com.ruoyi.erp.base.service.IErpSupplierService;

/**
 * 供应商档案 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/supplier")
public class ErpSupplierController extends BaseController
{
    @Autowired
    private IErpSupplierService supplierService;

    /**
     * 查询供应商档案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpSupplier erpSupplier)
    {
        startPage();
        List<ErpSupplier> list = supplierService.selectErpSupplierList(erpSupplier);
        return getDataTable(list);
    }

    /**
     * 获取供应商档案详细信息
     */
    @GetMapping(value = "/{supplierId}")
    public AjaxResult getInfo(@PathVariable("supplierId") Long supplierId)
    {
        return success(supplierService.selectErpSupplierById(supplierId));
    }

    /**
     * 新增供应商档案
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpSupplier erpSupplier)
    {
        return toAjax(supplierService.insertErpSupplier(erpSupplier));
    }

    /**
     * 修改供应商档案
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpSupplier erpSupplier)
    {
        return toAjax(supplierService.updateErpSupplier(erpSupplier));
    }

    /**
     * 删除供应商档案
     */
    @DeleteMapping("/{supplierId}")
    public AjaxResult remove(@PathVariable Long supplierId)
    {
        return toAjax(supplierService.deleteErpSupplierById(supplierId));
    }
}