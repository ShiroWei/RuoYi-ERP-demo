package com.ruoyi.erp.sale.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 销售出库单对象 erp_sale_outbound
 * 
 * @author erp
 */
public class ErpSaleOutbound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单ID */
    private Long outboundId;

    /** 入库单号 */
    private String outboundNo;

    /** 关联采购订单ID */
    private Long orderId;

    /** 关联采购订单编号（关联查询） */
    private String orderNo;

    /** 客户ID */
    private Long customerId;

    /** 客户名称（关联查询） */
    private String customerName;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库名称（关联查询） */
    private String warehouseName;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outboundDate;

    /** 入库金额 */
    private BigDecimal totalAmount;

    /** 单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成） */
    private String status;

    public Long getOutboundId()
    {
        return outboundId;
    }

    public void setOutboundId(Long outboundId)
    {
        this.outboundId = outboundId;
    }

    public String getOutboundNo()
    {
        return outboundNo;
    }

    public void setOutboundNo(String outboundNo)
    {
        this.outboundNo = outboundNo;
    }

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

    public Long getWarehouseId()
    {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId)
    {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public Date getOutboundDate()
    {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate)
    {
        this.outboundDate = outboundDate;
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
}