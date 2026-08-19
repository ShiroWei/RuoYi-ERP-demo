package com.ruoyi.erp.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存对象 erp_stock
 * 
 * @author erp
 */
public class ErpStock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存ID */
    private Long stockId;

    /** 仓库ID */
    private Long warehouseId;

    /** 物料ID */
    private Long materialId;

    /** 库存数量 */
    private java.math.BigDecimal quantity;

    /** 物料编码（关联查询） */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称（关联查询） */
    @Excel(name = "物料名称")
    private String materialName;

    /** 仓库名称（关联查询） */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 安全库存（关联查询） */
    @Excel(name = "安全库存")
    private java.math.BigDecimal safeStock;

    /** 计量单位（关联查询） */
    @Excel(name = "计量单位")
    private String unit;

    /** 采购单价（关联查询） */
    @Excel(name = "采购单价")
    private java.math.BigDecimal purchasePrice;

    /** 规格型号（关联查询） */
    @Excel(name = "规格型号")
    private String specification;

    /** 库存金额（关联计算） */
    @Excel(name = "库存金额")
    private java.math.BigDecimal amount;

    public void setStockId(Long stockId)
    {
        this.stockId = stockId;
    }

    public Long getStockId()
    {
        return stockId;
    }

    public void setWarehouseId(Long warehouseId)
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId()
    {
        return warehouseId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }

    public void setQuantity(java.math.BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public java.math.BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }

    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setSafeStock(java.math.BigDecimal safeStock)
    {
        this.safeStock = safeStock;
    }

    public java.math.BigDecimal getSafeStock()
    {
        return safeStock;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setPurchasePrice(java.math.BigDecimal purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public java.math.BigDecimal getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setAmount(java.math.BigDecimal amount)
    {
        this.amount = amount;
    }

    public java.math.BigDecimal getAmount()
    {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stockId", getStockId())
            .append("warehouseId", getWarehouseId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("warehouseName", getWarehouseName())
            .append("safeStock", getSafeStock())
            .append("unit", getUnit())
            .append("purchasePrice", getPurchasePrice())
            .append("specification", getSpecification())
            .append("amount", getAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}