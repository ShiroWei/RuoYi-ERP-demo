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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.erp.finance.domain.ErpPayment;
import com.ruoyi.erp.finance.service.IErpPaymentService;

/**
 * 收付款单 接口
 * 
 * @author erp
 */
@RestController
@RequestMapping("/finance/payment")
public class ErpPaymentController extends BaseController
{
    @Autowired
    private IErpPaymentService paymentService;

    /**
     * 查询收付款单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpPayment erpPayment)
    {
        startPage();
        List<ErpPayment> list = paymentService.selectErpPaymentList(erpPayment);
        return getDataTable(list);
    }

    /**
     * 获取收付款单详细信息
     */
    @GetMapping(value = "/{paymentId}")
    public AjaxResult getInfo(@PathVariable("paymentId") Long paymentId)
    {
        return success(paymentService.selectErpPaymentById(paymentId));
    }

    /**
     * 新增收付款单
     */
    @PostMapping
    public AjaxResult add(@RequestBody ErpPayment erpPayment)
    {
        return toAjax(paymentService.insertErpPayment(erpPayment));
    }

    /**
     * 修改收付款单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ErpPayment erpPayment)
    {
        return toAjax(paymentService.updateErpPayment(erpPayment));
    }

    /**
     * 删除收付款单
     */
    @DeleteMapping("/{paymentId}")
    public AjaxResult remove(@PathVariable Long paymentId)
    {
        return toAjax(paymentService.deleteErpPaymentById(paymentId));
    }

    /**
     * 提交审核
     */
    @PutMapping("/submit")
    public AjaxResult submit(@RequestParam Long paymentId)
    {
        return toAjax(paymentService.submitErpPayment(paymentId));
    }

    /**
     * 审核通过
     */
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long paymentId)
    {
        return toAjax(paymentService.approveErpPayment(paymentId));
    }

    /**
     * 审核驳回
     */
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam Long paymentId)
    {
        return toAjax(paymentService.rejectErpPayment(paymentId));
    }

    /**
     * 完成
     */
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long paymentId)
    {
        return toAjax(paymentService.completeErpPayment(paymentId));
    }
}