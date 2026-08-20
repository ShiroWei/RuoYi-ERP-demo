package com.ruoyi.erp.stock.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存盘点对象 erp_stock_check
 * 
 * @author erp
 */
public class ErpStockCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点ID */
    private Long checkId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String checkNo;

    /** 仓库ID */
    private Long warehouseId;

    /** 物料ID */
    private Long materialId;

    /** 账面数量 */
    @Excel(name = "账面数量")
    private java.math.BigDecimal bookQty;

    /** 实盘数量 */
    @Excel(name = "实盘数量")
    private java.math.BigDecimal actualQty;

    /** 差异数量 */
    @Excel(name = "差异数量")
    private java.math.BigDecimal diffQty;

    /** 盘点日期 */
    @Excel(name = "盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    /** 盘点状态（0未盘点 1已盘点） */
    @Excel(name = "盘点状态（0未盘点 1已盘点）")
    private String status;

    /** 物料编码（关联查询） */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称（关联查询） */
    @Excel(name = "物料名称")
    private String materialName;

    /** 仓库名称（关联查询） */
    @Excel(name = "仓库名称")
    private String warehouseName;

    public void setCheckId(Long checkId)
    {
        this.checkId = checkId;
    }

    public Long getCheckId()
    {
        return checkId;
    }

    public void setCheckNo(String checkNo)
    {
        this.checkNo = checkNo;
    }

    public String getCheckNo()
    {
        return checkNo;
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

    public void setBookQty(java.math.BigDecimal bookQty)
    {
        this.bookQty = bookQty;
    }

    public java.math.BigDecimal getBookQty()
    {
        return bookQty;
    }

    public void setActualQty(java.math.BigDecimal actualQty)
    {
        this.actualQty = actualQty;
    }

    public java.math.BigDecimal getActualQty()
    {
        return actualQty;
    }

    public void setDiffQty(java.math.BigDecimal diffQty)
    {
        this.diffQty = diffQty;
    }

    public java.math.BigDecimal getDiffQty()
    {
        return diffQty;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
            .append("checkId", getCheckId())
            .append("checkNo", getCheckNo())
            .append("warehouseId", getWarehouseId())
            .append("materialId", getMaterialId())
            .append("bookQty", getBookQty())
            .append("actualQty", getActualQty())
            .append("diffQty", getDiffQty())
            .append("checkDate", getCheckDate())
            .append("status", getStatus())
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