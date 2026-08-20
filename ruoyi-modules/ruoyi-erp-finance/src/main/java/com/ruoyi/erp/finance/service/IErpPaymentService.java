package com.ruoyi.erp.finance.service;

import java.util.List;
import com.ruoyi.erp.finance.domain.ErpPayment;

/**
 * 收付款单Service接口
 * 
 * @author erp
 */
public interface IErpPaymentService
{
    /**
     * 查询收付款单
     */
    public ErpPayment selectErpPaymentById(Long paymentId);

    /**
     * 查询收付款单列表
     */
    public List<ErpPayment> selectErpPaymentList(ErpPayment erpPayment);

    /**
     * 新增收付款单
     */
    public int insertErpPayment(ErpPayment erpPayment);

    /**
     * 修改收付款单
     */
    public int updateErpPayment(ErpPayment erpPayment);

    /**
     * 删除收付款单
     */
    public int deleteErpPaymentById(Long paymentId);

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpPayment(Long paymentId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpPayment(Long paymentId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpPayment(Long paymentId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpPayment(Long paymentId);
}