package com.ruoyi.erp.base.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 仓库档案对象 erp_warehouse
 * 
 * @author erp
 */
public class ErpWarehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 仓库类型 */
    private String warehouseType;

    /** 负责人 */
    private String manager;

    /** 联系电话 */
    private String phone;

    /** 地址 */
    private String address;

    /** 状态（0正常 1停用） */
    private String status;

    public Long getWarehouseId()
    {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId)
    {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode()
    {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode)
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseType()
    {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType)
    {
        this.warehouseType = warehouseType;
    }

    public String getManager()
    {
        return manager;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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
