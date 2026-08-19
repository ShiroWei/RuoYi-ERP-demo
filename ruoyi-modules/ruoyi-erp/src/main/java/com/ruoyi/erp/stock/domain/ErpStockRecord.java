package com.ruoyi.erp.stock.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 出入库记录对象 erp_stock_record
 * 
 * @author erp
 */
public class ErpStockRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 单号 */
    @Excel(name = "单号")
    private String recordNo;

    /** 出入库类型 */
    @Excel(name = "出入库类型")
    private String recordType;

    /** 方向（0出库 1入库） */
    @Excel(name = "方向（0出库 1入库）")
    private String direction;

    /** 仓库ID */
    private Long warehouseId;

    /** 物料ID */
    private Long materialId;

    /** 数量 */
    @Excel(name = "数量")
    private java.math.BigDecimal quantity;

    /** 关联业务单号 */
    @Excel(name = "关联业务单号")
    private String bizNo;

    /** 经办人 */
    @Excel(name = "经办人")
    private String operator;

    /** 发生日期 */
    @Excel(name = "发生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    /** 物料编码（关联查询） */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称（关联查询） */
    @Excel(name = "物料名称")
    private String materialName;

    /** 仓库名称（关联查询） */
    @Excel(name = "仓库名称")
    private String warehouseName;

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getRecordId()
    {
        return recordId;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    public String getRecordNo()
    {
        return recordNo;
    }

    public void setRecordType(String recordType)
    {
        this.recordType = recordType;
    }

    public String getRecordType()
    {
        return recordType;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getDirection()
    {
        return direction;
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

    public void setBizNo(String bizNo)
    {
        this.bizNo = bizNo;
    }

    public String getBizNo()
    {
        return bizNo;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setRecordDate(Date recordDate)
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate()
    {
        return recordDate;
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
            .append("recordId", getRecordId())
            .append("recordNo", getRecordNo())
            .append("recordType", getRecordType())
            .append("direction", getDirection())
            .append("warehouseId", getWarehouseId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("bizNo", getBizNo())
            .append("operator", getOperator())
            .append("recordDate", getRecordDate())
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