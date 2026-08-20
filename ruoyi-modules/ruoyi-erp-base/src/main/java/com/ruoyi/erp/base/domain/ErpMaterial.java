package com.ruoyi.erp.base.domain;

import java.math.BigDecimal;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 物料档案对象 erp_material
 * 
 * @author erp
 */
public class ErpMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料ID */
    private Long materialId;

    /** 物料编码 */
    private String materialCode;

    /** 物料名称 */
    private String materialName;

    /** 物料分类 */
    private String category;

    /** 规格型号 */
    private String specification;

    /** 计量单位 */
    private String unit;

    /** 采购单价 */
    private BigDecimal purchasePrice;

    /** 销售单价 */
    private BigDecimal salePrice;

    /** 安全库存 */
    private BigDecimal safeStock;

    /** 状态（0正常 1停用） */
    private String status;

    public Long getMaterialId()
    {
        return materialId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }

    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public BigDecimal getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice)
    {
        this.salePrice = salePrice;
    }

    public BigDecimal getSafeStock()
    {
        return safeStock;
    }

    public void setSafeStock(BigDecimal safeStock)
    {
        this.safeStock = safeStock;
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
