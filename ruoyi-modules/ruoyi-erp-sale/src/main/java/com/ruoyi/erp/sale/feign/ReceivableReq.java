package com.ruoyi.erp.sale.feign;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 应收账款生成请求（与财务服务 ErpReceivable 字段对齐）
 * 
 * @author erp
 */
public class ReceivableReq
{
    private String billNo;
    private String billType;
    private Long customerId;
    private BigDecimal amount;
    private Date dueDate;

    public String getBillNo()
    {
        return billNo;
    }

    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillType()
    {
        return billType;
    }

    public void setBillType(String billType)
    {
        this.billType = billType;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }
}
