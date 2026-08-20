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
import com.ruoyi.erp.finance.domain.ErpReceivable;
import com.ruoyi.erp.finance.service.IErpReceivableService;

/**
 * 应收账款 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/finance/receivable")
public class ErpReceivableController extends BaseController
{
    @Autowired
    private IErpReceivableService receivableService;

    /**
     * 查询应收账款列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpReceivable erpReceivable)
    {
        startPage();
        List<ErpReceivable> list = receivableService.selectErpReceivableList(erpReceivable);
        return getDataTable(list);
    }

    /**
     * 生成应收账款（内部接口，供销售服务 Feign 调用）
     */
    @PostMapping("/internal/receivable")
    public AjaxResult generate(@RequestBody ErpReceivable erpReceivable)
    {
        return toAjax(receivableService.generateErpReceivable(erpReceivable));
    }

    /**
     * 获取应收账款详细信息
     */
    @GetMapping(value = "/{receivableId}")
    public AjaxResult getInfo(@PathVariable("receivableId") Long receivableId)
    {
        return success(receivableService.selectErpReceivableById(receivableId));
    }

    /**
     * 新增应收账款
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpReceivable erpReceivable)
    {
        return toAjax(receivableService.insertErpReceivable(erpReceivable));
    }

    /**
     * 修改应收账款
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpReceivable erpReceivable)
    {
        return toAjax(receivableService.updateErpReceivable(erpReceivable));
    }

    /**
     * 删除应收账款
     */
    @DeleteMapping("/{receivableId}")
    public AjaxResult remove(@PathVariable Long receivableId)
    {
        return toAjax(receivableService.deleteErpReceivableById(receivableId));
    }
}