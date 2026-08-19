package com.ruoyi.erp.finance.controller;

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
import com.ruoyi.erp.finance.domain.ErpPayable;
import com.ruoyi.erp.finance.service.IErpPayableService;

/**
 * 应付账款 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/finance/payable")
public class ErpPayableController extends BaseController
{
    @Autowired
    private IErpPayableService payableService;

    /**
     * 查询应付账款列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpPayable erpPayable)
    {
        startPage();
        List<ErpPayable> list = payableService.selectErpPayableList(erpPayable);
        return getDataTable(list);
    }

    /**
     * 获取应付账款详细信息
     */
    @GetMapping(value = "/{payableId}")
    public AjaxResult getInfo(@PathVariable("payableId") Long payableId)
    {
        return success(payableService.selectErpPayableById(payableId));
    }

    /**
     * 新增应付账款
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpPayable erpPayable)
    {
        return toAjax(payableService.insertErpPayable(erpPayable));
    }

    /**
     * 修改应付账款
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpPayable erpPayable)
    {
        return toAjax(payableService.updateErpPayable(erpPayable));
    }

    /**
     * 删除应付账款
     */
    @DeleteMapping("/{payableId}")
    public AjaxResult remove(@PathVariable Long payableId)
    {
        return toAjax(payableService.deleteErpPayableById(payableId));
    }
}