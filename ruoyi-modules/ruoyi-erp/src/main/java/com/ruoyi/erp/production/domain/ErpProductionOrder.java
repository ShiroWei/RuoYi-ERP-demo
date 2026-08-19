package com.ruoyi.erp.production.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 生产工单对象 erp_production_order
 * 
 * @author erp
 */
public class ErpProductionOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工单ID */
    private Long orderId;

    /** 工单编号 */
    @Excel(name = "工单编号")
    private String orderNo;

    /** 成品物料ID */
    private Long productId;

    /** 成品编码 */
    @Excel(name = "成品编码")
    private String productCode;

    /** 成品名称 */
    @Excel(name = "成品名称")
    private String productName;

    /** 计划数量 */
    @Excel(name = "计划数量")
    private java.math.BigDecimal planQty;

    /** 完工数量 */
    @Excel(name = "完工数量")
    private java.math.BigDecimal finishQty;

    /** 下达日期 */
    @Excel(name = "下达日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /** 计划开工日期 */
    @Excel(name = "计划开工日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planStartDate;

    /** 计划完工日期 */
    @Excel(name = "计划完工日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planEndDate;

    /** 状态（0未开始 1生产中 2已完工 3已关闭） */
    @Excel(name = "状态（0未开始 1生产中 2已完工 3已关闭）")
    private String status;

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setPlanQty(java.math.BigDecimal planQty)
    {
        this.planQty = planQty;
    }

    public java.math.BigDecimal getPlanQty()
    {
        return planQty;
    }

    public void setFinishQty(java.math.BigDecimal finishQty)
    {
        this.finishQty = finishQty;
    }

    public java.math.BigDecimal getFinishQty()
    {
        return finishQty;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setPlanStartDate(Date planStartDate)
    {
        this.planStartDate = planStartDate;
    }

    public Date getPlanStartDate()
    {
        return planStartDate;
    }

    public void setPlanEndDate(Date planEndDate)
    {
        this.planEndDate = planEndDate;
    }

    public Date getPlanEndDate()
    {
        return planEndDate;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("planQty", getPlanQty())
            .append("finishQty", getFinishQty())
            .append("orderDate", getOrderDate())
            .append("planStartDate", getPlanStartDate())
            .append("planEndDate", getPlanEndDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}