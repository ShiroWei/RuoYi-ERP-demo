package com.ruoyi.erp.finance.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 收付款单对象 erp_payment
 * 
 * @author erp
 */
public class ErpPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单ID */
    private Long paymentId;

    /** 单号 */
    @Excel(name = "单号")
    private String paymentNo;

    /** 类型（1收款 2付款） */
    @Excel(name = "类型（1收款 2付款）")
    private String paymentType;

    /** 关联业务单号 */
    @Excel(name = "关联业务单号")
    private String billNo;

    /** 往来单位类型（客户/供应商） */
    @Excel(name = "往来单位类型（客户/供应商）")
    private String partnerType;

    /** 往来单位ID */
    private Long partnerId;

    /** 金额 */
    @Excel(name = "金额")
    private java.math.BigDecimal amount;

    /** 收付款日期 */
    @Excel(name = "收付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentDate;

    /** 单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成） */
    @Excel(name = "单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）")
    private String status;

    /** 往来单位名称（关联查询） */
    @Excel(name = "往来单位名称")
    private String partnerName;

    public void setPaymentId(Long paymentId)
    {
        this.paymentId = paymentId;
    }

    public Long getPaymentId()
    {
        return paymentId;
    }

    public void setPaymentNo(String paymentNo)
    {
        this.paymentNo = paymentNo;
    }

    public String getPaymentNo()
    {
        return paymentNo;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }

    public void setPartnerType(String partnerType)
    {
        this.partnerType = partnerType;
    }

    public String getPartnerType()
    {
        return partnerType;
    }

    public void setPartnerId(Long partnerId)
    {
        this.partnerId = partnerId;
    }

    public Long getPartnerId()
    {
        return partnerId;
    }

    public void setAmount(java.math.BigDecimal amount)
    {
        this.amount = amount;
    }

    public java.math.BigDecimal getAmount()
    {
        return amount;
    }

    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setPartnerName(String partnerName)
    {
        this.partnerName = partnerName;
    }

    public String getPartnerName()
    {
        return partnerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("paymentId", getPaymentId())
            .append("paymentNo", getPaymentNo())
            .append("paymentType", getPaymentType())
            .append("billNo", getBillNo())
            .append("partnerType", getPartnerType())
            .append("partnerId", getPartnerId())
            .append("amount", getAmount())
            .append("paymentDate", getPaymentDate())
            .append("status", getStatus())
            .append("partnerName", getPartnerName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}