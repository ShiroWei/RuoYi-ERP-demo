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
import com.ruoyi.erp.base.domain.ErpCustomer;
import com.ruoyi.erp.base.service.IErpCustomerService;

/**
 * 客户档案 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/customer")
public class ErpCustomerController extends BaseController
{
    @Autowired
    private IErpCustomerService customerService;

    /**
     * 查询客户档案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpCustomer erpCustomer)
    {
        startPage();
        List<ErpCustomer> list = customerService.selectErpCustomerList(erpCustomer);
        return getDataTable(list);
    }

    /**
     * 获取客户档案详细信息
     */
    @GetMapping(value = "/{customerId}")
    public AjaxResult getInfo(@PathVariable("customerId") Long customerId)
    {
        return success(customerService.selectErpCustomerById(customerId));
    }

    /**
     * 新增客户档案
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpCustomer erpCustomer)
    {
        return toAjax(customerService.insertErpCustomer(erpCustomer));
    }

    /**
     * 修改客户档案
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpCustomer erpCustomer)
    {
        return toAjax(customerService.updateErpCustomer(erpCustomer));
    }

    /**
     * 删除客户档案
     */
    @DeleteMapping("/{customerId}")
    public AjaxResult remove(@PathVariable Long customerId)
    {
        return toAjax(customerService.deleteErpCustomerById(customerId));
    }
}