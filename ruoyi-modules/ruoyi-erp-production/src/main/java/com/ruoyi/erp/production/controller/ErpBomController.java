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
import com.ruoyi.erp.production.domain.ErpBom;
import com.ruoyi.erp.production.service.IErpBomService;

/**
 * 物料清单(BOM) 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/production/bom")
public class ErpBomController extends BaseController
{
    @Autowired
    private IErpBomService bomService;

    /**
     * 查询物料清单(BOM)列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpBom erpBom)
    {
        startPage();
        List<ErpBom> list = bomService.selectErpBomList(erpBom);
        return getDataTable(list);
    }

    /**
     * 获取物料清单(BOM)详细信息
     */
    @GetMapping(value = "/{bomId}")
    public AjaxResult getInfo(@PathVariable("bomId") Long bomId)
    {
        return success(bomService.selectErpBomById(bomId));
    }

    /**
     * 新增物料清单(BOM)
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpBom erpBom)
    {
        return toAjax(bomService.insertErpBom(erpBom));
    }

    /**
     * 修改物料清单(BOM)
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpBom erpBom)
    {
        return toAjax(bomService.updateErpBom(erpBom));
    }

    /**
     * 删除物料清单(BOM)
     */
    @DeleteMapping("/{bomId}")
    public AjaxResult remove(@PathVariable Long bomId)
    {
        return toAjax(bomService.deleteErpBomById(bomId));
    }
}