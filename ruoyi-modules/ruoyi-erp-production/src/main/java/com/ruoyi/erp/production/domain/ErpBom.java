package com.ruoyi.erp.production.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 物料清单(BOM)主表对象 erp_bom
 * 
 * @author erp
 */
public class ErpBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** BOM ID */
    private Long bomId;

    /** BOM编号 */
    @Excel(name = "BOM编号")
    private String bomNo;

    /** 成品物料ID */
    private Long productId;

    /** 成品编码 */
    @Excel(name = "成品编码")
    private String productCode;

    /** 成品名称 */
    @Excel(name = "成品名称")
    private String productName;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态（0正常 1停用）")
    private String status;

    /** BOM明细列表 */
    private List<ErpBomItem> items;

    public void setBomId(Long bomId)
    {
        this.bomId = bomId;
    }

    public Long getBomId()
    {
        return bomId;
    }

    public void setBomNo(String bomNo)
    {
        this.bomNo = bomNo;
    }

    public String getBomNo()
    {
        return bomNo;
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

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setItems(List<ErpBomItem> items)
    {
        this.items = items;
    }

    public List<ErpBomItem> getItems()
    {
        return items;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bomId", getBomId())
            .append("bomNo", getBomNo())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("unit", getUnit())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}