package com.ruoyi.erp.purchase.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 采购入库单对象 erp_purchase_inbound
 * 
 * @author erp
 */
public class ErpPurchaseInbound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单ID */
    private Long inboundId;

    /** 入库单号 */
    private String inboundNo;

    /** 关联采购订单ID */
    private Long orderId;

    /** 关联采购订单编号（关联查询） */
    private String orderNo;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商名称（关联查询） */
    private String supplierName;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库名称（关联查询） */
    private String warehouseName;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inboundDate;

    /** 入库金额 */
    private BigDecimal totalAmount;

    /** 单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成） */
    private String status;

    public Long getInboundId()
    {
        return inboundId;
    }

    public void setInboundId(Long inboundId)
    {
        this.inboundId = inboundId;
    }

    public String getInboundNo()
    {
        return inboundNo;
    }

    public void setInboundNo(String inboundNo)
    {
        this.inboundNo = inboundNo;
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

    public Long getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
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

    public Date getInboundDate()
    {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate)
    {
        this.inboundDate = inboundDate;
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