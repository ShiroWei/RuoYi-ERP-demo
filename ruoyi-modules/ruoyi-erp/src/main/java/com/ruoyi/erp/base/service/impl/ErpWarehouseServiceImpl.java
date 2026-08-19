package com.ruoyi.erp.base.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.base.domain.ErpWarehouse;
import com.ruoyi.erp.base.mapper.ErpWarehouseMapper;
import com.ruoyi.erp.base.service.IErpWarehouseService;

/**
 * 仓库档案Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpWarehouseServiceImpl implements IErpWarehouseService
{
    @Autowired
    private ErpWarehouseMapper erpWarehouseMapper;

    /**
     * 查询仓库档案
     */
    @Override
    public ErpWarehouse selectErpWarehouseById(Long warehouseId)
    {
        return erpWarehouseMapper.selectErpWarehouseById(warehouseId);
    }

    /**
     * 查询仓库档案列表
     */
    @Override
    public List<ErpWarehouse> selectErpWarehouseList(ErpWarehouse erpWarehouse)
    {
        return erpWarehouseMapper.selectErpWarehouseList(erpWarehouse);
    }

    /**
     * 新增仓库档案
     */
    @Override
    public int insertErpWarehouse(ErpWarehouse erpWarehouse)
    {
        erpWarehouse.setCreateBy(SecurityUtils.getUsername());
        erpWarehouse.setCreateTime(DateUtils.getNowDate());
        return erpWarehouseMapper.insertErpWarehouse(erpWarehouse);
    }

    /**
     * 修改仓库档案
     */
    @Override
    public int updateErpWarehouse(ErpWarehouse erpWarehouse)
    {
        erpWarehouse.setUpdateBy(SecurityUtils.getUsername());
        erpWarehouse.setUpdateTime(DateUtils.getNowDate());
        return erpWarehouseMapper.updateErpWarehouse(erpWarehouse);
    }

    /**
     * 删除仓库档案
     */
    @Override
    public int deleteErpWarehouseById(Long warehouseId)
    {
        return erpWarehouseMapper.deleteErpWarehouseById(warehouseId);
    }
}