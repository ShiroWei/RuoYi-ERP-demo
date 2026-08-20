package com.ruoyi.erp.purchase.feign;

import java.math.BigDecimal;

/**
 * 库存调整请求（与库存服务 ErpStock 字段对齐）
 * 
 * @author erp
 */
public class StockAdjustReq
{
    private Long warehouseId;
    private Long materialId;
    private BigDecimal quantity;
    private Boolean strict;

    public Long getWarehouseId()
    {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId)
    {
        this.warehouseId = warehouseId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public Boolean getStrict()
    {
        return strict;
    }

    public void setStrict(Boolean strict)
    {
        this.strict = strict;
    }
}
