package com.ruoyi.erp.purchase.feign;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 应付账款生成请求（与财务服务 ErpPayable 字段对齐）
 * 
 * @author erp
 */
public class PayableReq
{
    private String billNo;
    private String billType;
    private Long supplierId;
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

    public Long getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
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
