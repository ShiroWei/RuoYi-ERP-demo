package com.ruoyi.erp.finance.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 应收账款对象 erp_receivable
 * 
 * @author erp
 */
public class ErpReceivable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应收ID */
    private Long receivableId;

    /** 业务单号 */
    @Excel(name = "业务单号")
    private String billNo;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String billType;

    /** 客户ID */
    private Long customerId;

    /** 应收金额 */
    @Excel(name = "应收金额")
    private java.math.BigDecimal amount;

    /** 已收金额 */
    @Excel(name = "已收金额")
    private java.math.BigDecimal receivedAmount;

    /** 未收金额 */
    @Excel(name = "未收金额")
    private java.math.BigDecimal balance;

    /** 到期日 */
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 状态（0未结清 1部分结清 2已结清） */
    @Excel(name = "状态（0未结清 1部分结清 2已结清）")
    private String status;

    /** 客户名称（关联查询） */
    @Excel(name = "客户名称")
    private String customerName;

    public void setReceivableId(Long receivableId)
    {
        this.receivableId = receivableId;
    }

    public Long getReceivableId()
    {
        return receivableId;
    }

    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }

    public void setBillType(String billType)
    {
        this.billType = billType;
    }

    public String getBillType()
    {
        return billType;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setAmount(java.math.BigDecimal amount)
    {
        this.amount = amount;
    }

    public java.math.BigDecimal getAmount()
    {
        return amount;
    }

    public void setReceivedAmount(java.math.BigDecimal receivedAmount)
    {
        this.receivedAmount = receivedAmount;
    }

    public java.math.BigDecimal getReceivedAmount()
    {
        return receivedAmount;
    }

    public void setBalance(java.math.BigDecimal balance)
    {
        this.balance = balance;
    }

    public java.math.BigDecimal getBalance()
    {
        return balance;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("receivableId", getReceivableId())
            .append("billNo", getBillNo())
            .append("billType", getBillType())
            .append("customerId", getCustomerId())
            .append("amount", getAmount())
            .append("receivedAmount", getReceivedAmount())
            .append("balance", getBalance())
            .append("dueDate", getDueDate())
            .append("status", getStatus())
            .append("customerName", getCustomerName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}