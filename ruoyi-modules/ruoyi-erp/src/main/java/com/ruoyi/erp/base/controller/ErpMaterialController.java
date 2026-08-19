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
import com.ruoyi.erp.base.domain.ErpMaterial;
import com.ruoyi.erp.base.service.IErpMaterialService;

/**
 * 物料档案 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/material")
public class ErpMaterialController extends BaseController
{
    @Autowired
    private IErpMaterialService materialService;

    /**
     * 查询物料档案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpMaterial erpMaterial)
    {
        startPage();
        List<ErpMaterial> list = materialService.selectErpMaterialList(erpMaterial);
        return getDataTable(list);
    }

    /**
     * 获取物料档案详细信息
     */
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(materialService.selectErpMaterialById(materialId));
    }

    /**
     * 新增物料档案
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpMaterial erpMaterial)
    {
        return toAjax(materialService.insertErpMaterial(erpMaterial));
    }

    /**
     * 修改物料档案
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpMaterial erpMaterial)
    {
        return toAjax(materialService.updateErpMaterial(erpMaterial));
    }

    /**
     * 删除物料档案
     */
    @DeleteMapping("/{materialId}")
    public AjaxResult remove(@PathVariable Long materialId)
    {
        return toAjax(materialService.deleteErpMaterialById(materialId));
    }
}