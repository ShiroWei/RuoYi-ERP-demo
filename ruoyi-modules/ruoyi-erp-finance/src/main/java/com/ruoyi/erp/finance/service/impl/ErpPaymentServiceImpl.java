package com.ruoyi.erp.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.finance.domain.ErpPayment;
import com.ruoyi.erp.finance.domain.ErpPayable;
import com.ruoyi.erp.finance.domain.ErpReceivable;
import com.ruoyi.erp.finance.mapper.ErpPayableMapper;
import com.ruoyi.erp.finance.mapper.ErpPaymentMapper;
import com.ruoyi.erp.finance.mapper.ErpReceivableMapper;
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

    @Autowired
    private ErpReceivableMapper receivableMapper;

    @Autowired
    private ErpPayableMapper payableMapper;

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
        fillAndValidateBill(erpPayment);
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
        fillAndValidateBill(erpPayment);
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
    @Transactional
    public int completeErpPayment(Long paymentId)
    {
        ErpPayment payment = paymentMapper.selectErpPaymentById(paymentId);
        if (payment == null)
        {
            throw new ServiceException("收付款单不存在");
        }
        if ("4".equals(payment.getStatus()))
        {
            return 1;
        }
        settleBill(payment);
        return updateStatus(paymentId, "4");
    }

    private void fillAndValidateBill(ErpPayment payment)
    {
        if (payment.getBillNo() == null || payment.getBillNo().trim().isEmpty())
        {
            throw new ServiceException("请选择关联应收或应付单据");
        }
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new ServiceException("收付款金额必须大于0");
        }
        if ("1".equals(payment.getPaymentType()))
        {
            ErpReceivable receivable = getReceivable(payment.getBillNo());
            if (receivable == null || "2".equals(receivable.getStatus()))
            {
                throw new ServiceException("关联应收单不存在或已结清");
            }
            validateAmount(payment.getAmount(), receivable.getBalance());
            payment.setPartnerType("客户");
            payment.setPartnerId(receivable.getCustomerId());
        }
        else if ("2".equals(payment.getPaymentType()))
        {
            ErpPayable payable = getPayable(payment.getBillNo());
            if (payable == null || "2".equals(payable.getStatus()))
            {
                throw new ServiceException("关联应付单不存在或已结清");
            }
            validateAmount(payment.getAmount(), payable.getBalance());
            payment.setPartnerType("供应商");
            payment.setPartnerId(payable.getSupplierId());
        }
        else
        {
            throw new ServiceException("收付款类型不正确");
        }
    }

    private void settleBill(ErpPayment payment)
    {
        fillAndValidateBill(payment);
        if ("1".equals(payment.getPaymentType()))
        {
            ErpReceivable receivable = getReceivable(payment.getBillNo());
            BigDecimal received = value(receivable.getReceivedAmount()).add(payment.getAmount());
            BigDecimal balance = receivable.getAmount().subtract(received);
            receivable.setReceivedAmount(received);
            receivable.setBalance(balance);
            receivable.setStatus(balance.signum() == 0 ? "2" : "1");
            receivable.setUpdateBy(SecurityUtils.getUsername());
            receivable.setUpdateTime(DateUtils.getNowDate());
            receivableMapper.updateErpReceivable(receivable);
        }
        else
        {
            ErpPayable payable = getPayable(payment.getBillNo());
            BigDecimal paid = value(payable.getPaidAmount()).add(payment.getAmount());
            BigDecimal balance = payable.getAmount().subtract(paid);
            payable.setPaidAmount(paid);
            payable.setBalance(balance);
            payable.setStatus(balance.signum() == 0 ? "2" : "1");
            payable.setUpdateBy(SecurityUtils.getUsername());
            payable.setUpdateTime(DateUtils.getNowDate());
            payableMapper.updateErpPayable(payable);
        }
    }

    private ErpReceivable getReceivable(String billNo)
    {
        ErpReceivable query = new ErpReceivable();
        query.setBillNo(billNo);
        List<ErpReceivable> list = receivableMapper.selectErpReceivableList(query);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    private ErpPayable getPayable(String billNo)
    {
        ErpPayable query = new ErpPayable();
        query.setBillNo(billNo);
        List<ErpPayable> list = payableMapper.selectErpPayableList(query);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    private void validateAmount(BigDecimal amount, BigDecimal balance)
    {
        if (balance == null || amount.compareTo(balance) > 0)
        {
            throw new ServiceException("收付款金额不能超过未结金额");
        }
    }

    private BigDecimal value(BigDecimal amount)
    {
        return amount == null ? BigDecimal.ZERO : amount;
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
