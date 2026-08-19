package com.ruoyi.erp.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.stock.domain.ErpStock;
import com.ruoyi.erp.stock.mapper.ErpStockMapper;
import com.ruoyi.erp.stock.service.IErpStockService;

/**
 * 库存Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpStockServiceImpl implements IErpStockService
{
    @Autowired
    private ErpStockMapper stockMapper;

    /**
     * 查询库存
     */
    @Override
    public ErpStock selectErpStockById(Long stockId)
    {
        return stockMapper.selectErpStockById(stockId);
    }

    /**
     * 查询库存列表
     */
    @Override
    public List<ErpStock> selectErpStockList(ErpStock erpStock)
    {
        return stockMapper.selectErpStockList(erpStock);
    }

    /**
     * 新增库存
     */
    @Override
    public int insertErpStock(ErpStock erpStock)
    {
        erpStock.setCreateBy(SecurityUtils.getUsername());
        erpStock.setCreateTime(DateUtils.getNowDate());
        return stockMapper.insertErpStock(erpStock);
    }

    /**
     * 修改库存
     */
    @Override
    public int updateErpStock(ErpStock erpStock)
    {
        erpStock.setUpdateBy(SecurityUtils.getUsername());
        erpStock.setUpdateTime(DateUtils.getNowDate());
        return stockMapper.updateErpStock(erpStock);
    }

    /**
     * 删除库存
     */
    @Override
    public int deleteErpStockById(Long stockId)
    {
        return stockMapper.deleteErpStockById(stockId);
    }
}