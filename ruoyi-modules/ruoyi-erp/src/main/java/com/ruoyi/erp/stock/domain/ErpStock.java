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
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}