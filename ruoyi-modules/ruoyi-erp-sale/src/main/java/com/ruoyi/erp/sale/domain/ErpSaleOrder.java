package com.ruoyi.erp.sale.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 销售订单主表对象 erp_sale_order
 * 
 * @author erp
 */
public class ErpSaleOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 客户ID */
    private Long customerId;

    /** 客户名称（关联查询） */
    private String customerName;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /** 订单总额 */
    private BigDecimal totalAmount;

    /** 单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成） */
    private String status;

    /** 明细行 */
    private List<ErpSaleOrderItem> items;

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<ErpSaleOrderItem> getItems()
    {
        return items;
    }

    public void setItems(List<ErpSaleOrderItem> items)
    {
        this.items = items;
    }
}