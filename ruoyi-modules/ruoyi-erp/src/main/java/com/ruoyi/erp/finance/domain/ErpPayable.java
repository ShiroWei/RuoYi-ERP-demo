package com.ruoyi.erp.finance.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 应付账款对象 erp_payable
 * 
 * @author erp
 */
public class ErpPayable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应付ID */
    private Long payableId;

    /** 业务单号 */
    @Excel(name = "业务单号")
    private String billNo;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String billType;

    /** 供应商ID */
    private Long supplierId;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private java.math.BigDecimal amount;

    /** 已付金额 */
    @Excel(name = "已付金额")
    private java.math.BigDecimal paidAmount;

    /** 未付金额 */
    @Excel(name = "未付金额")
    private java.math.BigDecimal balance;

    /** 到期日 */
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 状态（0未结清 1部分结清 2已结清） */
    @Excel(name = "状态（0未结清 1部分结清 2已结清）")
    private String status;

    /** 供应商名称（关联查询） */
    @Excel(name = "供应商名称")
    private String supplierName;

    public void setPayableId(Long payableId)
    {
        this.payableId = payableId;
    }

    public Long getPayableId()
    {
        return payableId;
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

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId()
    {
        return supplierId;
    }

    public void setAmount(java.math.BigDecimal amount)
    {
        this.amount = amount;
    }

    public java.math.BigDecimal getAmount()
    {
        return amount;
    }

    public void setPaidAmount(java.math.BigDecimal paidAmount)
    {
        this.paidAmount = paidAmount;
    }

    public java.math.BigDecimal getPaidAmount()
    {
        return paidAmount;
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

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("payableId", getPayableId())
            .append("billNo", getBillNo())
            .append("billType", getBillType())
            .append("supplierId", getSupplierId())
            .append("amount", getAmount())
            .append("paidAmount", getPaidAmount())
            .append("balance", getBalance())
            .append("dueDate", getDueDate())
            .append("status", getStatus())
            .append("supplierName", getSupplierName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}