package com.ruoyi.erp.base.mapper;

import java.util.List;
import com.ruoyi.erp.base.domain.ErpWarehouse;

/**
 * 仓库档案Mapper接口
 * 
 * @author erp
 */
public interface ErpWarehouseMapper
{
    /**
     * 查询仓库档案
     */
    public ErpWarehouse selectErpWarehouseById(Long warehouseId);

    /**
     * 查询仓库档案列表
     */
    public List<ErpWarehouse> selectErpWarehouseList(ErpWarehouse erpWarehouse);

    /**
     * 新增仓库档案
     */
    public int insertErpWarehouse(ErpWarehouse erpWarehouse);

    /**
     * 修改仓库档案
     */
    public int updateErpWarehouse(ErpWarehouse erpWarehouse);

    /**
     * 删除仓库档案
     */
    public int deleteErpWarehouseById(Long warehouseId);
}
