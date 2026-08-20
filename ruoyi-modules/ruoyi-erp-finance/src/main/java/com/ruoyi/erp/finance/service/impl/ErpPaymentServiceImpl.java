package com.ruoyi.erp.finance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.finance.domain.ErpPayment;
import com.ruoyi.erp.finance.mapper.ErpPaymentMapper;
import com.ruoyi.erp.finance.service.IErpPaymentService;

/**
 * 收付款单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPaymentServiceImpl implements IErpPaymentService
{
    @Autowired
    private ErpPaymentMapper paymentMapper;

    /**
     * 查询收付款单
     */
    @Override
    public ErpPayment selectErpPaymentById(Long paymentId)
    {
        return paymentMapper.selectErpPaymentById(paymentId);
    }

    /**
     * 查询收付款单列表
     */
    @Override
    public List<ErpPayment> selectErpPaymentList(ErpPayment erpPayment)
    {
        return paymentMapper.selectErpPaymentList(erpPayment);
    }

    /**
     * 新增收付款单
     */
    @Override
    public int insertErpPayment(ErpPayment erpPayment)
    {
        erpPayment.setPaymentNo(generatePaymentNo());
        erpPayment.setStatus("0");
        erpPayment.setCreateBy(SecurityUtils.getUsername());
        erpPayment.setCreateTime(DateUtils.getNowDate());
        return paymentMapper.insertErpPayment(erpPayment);
    }

    /**
     * 修改收付款单
     */
    @Override
    public int updateErpPayment(ErpPayment erpPayment)
    {
        erpPayment.setUpdateBy(SecurityUtils.getUsername());
        erpPayment.setUpdateTime(DateUtils.getNowDate());
        return paymentMapper.updateErpPayment(erpPayment);
    }

    /**
     * 删除收付款单（仅草稿或已驳回可删）
     */
    @Override
    public int deleteErpPaymentById(Long paymentId)
    {
        ErpPayment payment = paymentMapper.selectErpPaymentById(paymentId);
        if (payment != null && !"0".equals(payment.getStatus()) && !"3".equals(payment.getStatus()))
        {
            throw new ServiceException("仅草稿或已驳回单据可删除");
        }
        return paymentMapper.deleteErpPaymentById(paymentId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpPayment(Long paymentId)
    {
        return updateStatus(paymentId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpPayment(Long paymentId)
    {
        return updateStatus(paymentId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpPayment(Long paymentId)
    {
        return updateStatus(paymentId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpPayment(Long paymentId)
    {
        return updateStatus(paymentId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long paymentId, String status)
    {
        ErpPayment payment = new ErpPayment();
        payment.setPaymentId(paymentId);
        payment.setStatus(status);
        payment.setUpdateBy(SecurityUtils.getUsername());
        payment.setUpdateTime(DateUtils.getNowDate());
        return paymentMapper.updateErpPayment(payment);
    }

    /**
     * 生成单号：PAY + yyyyMMddHHmmss
     */
    private String generatePaymentNo()
    {
        return "PAY" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}