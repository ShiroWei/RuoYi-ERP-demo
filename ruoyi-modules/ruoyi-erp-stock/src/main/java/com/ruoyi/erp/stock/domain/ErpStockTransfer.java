package com.ruoyi.erp.stock.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存调拨对象 erp_stock_transfer
 * 
 * @author erp
 */
public class ErpStockTransfer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨ID */
    private Long transferId;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String transferNo;

    /** 调出仓库ID */
    private Long fromWarehouseId;

    /** 调入仓库ID */
    private Long toWarehouseId;

    /** 物料ID */
    private Long materialId;

    /** 调拨数量 */
    @Excel(name = "调拨数量")
    private java.math.BigDecimal quantity;

    /** 调拨日期 */
    @Excel(name = "调拨日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transferDate;

    /** 单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成） */
    @Excel(name = "单据状态（0草稿 1待审核 2审核通过 3已驳回 4已完成）")
    private String status;

    /** 物料编码（关联查询） */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称（关联查询） */
    @Excel(name = "物料名称")
    private String materialName;

    /** 调出仓库名称（关联查询） */
    @Excel(name = "调出仓库名称")
    private String fromWarehouseName;

    /** 调入仓库名称（关联查询） */
    @Excel(name = "调入仓库名称")
    private String toWarehouseName;

    public void setTransferId(Long transferId)
    {
        this.transferId = transferId;
    }

    public Long getTransferId()
    {
        return transferId;
    }

    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }

    public String getTransferNo()
    {
        return transferNo;
    }

    public void setFromWarehouseId(Long fromWarehouseId)
    {
        this.fromWarehouseId = fromWarehouseId;
    }

    public Long getFromWarehouseId()
    {
        return fromWarehouseId;
    }

    public void setToWarehouseId(Long toWarehouseId)
    {
        this.toWarehouseId = toWarehouseId;
    }

    public Long getToWarehouseId()
    {
        return toWarehouseId;
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

    public void setTransferDate(Date transferDate)
    {
        this.transferDate = transferDate;
    }

    public Date getTransferDate()
    {
        return transferDate;
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

    public void setFromWarehouseName(String fromWarehouseName)
    {
        this.fromWarehouseName = fromWarehouseName;
    }

    public String getFromWarehouseName()
    {
        return fromWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName)
    {
        this.toWarehouseName = toWarehouseName;
    }

    public String getToWarehouseName()
    {
        return toWarehouseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("transferId", getTransferId())
            .append("transferNo", getTransferNo())
            .append("fromWarehouseId", getFromWarehouseId())
            .append("toWarehouseId", getToWarehouseId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("transferDate", getTransferDate())
            .append("status", getStatus())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("fromWarehouseName", getFromWarehouseName())
            .append("toWarehouseName", getToWarehouseName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}