package com.ruoyi.erp.stock.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.exception.ServiceException;
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

    /**
     * 调整库存数量：quantity 为正则增加、为负则减少。
     * strict=true 时减少超出当前库存则抛库存不足；strict=false 时不足截断为 0。
     */
    @Override
    public int adjustStock(ErpStock erpStock)
    {
        if (erpStock.getWarehouseId() == null || erpStock.getMaterialId() == null || erpStock.getQuantity() == null)
        {
            throw new ServiceException("调整库存参数不完整");
        }
        BigDecimal delta = erpStock.getQuantity();
        if (delta.compareTo(BigDecimal.ZERO) < 0)
        {
            ErpStock current = stockMapper.selectErpStockByWarehouseAndMaterial(erpStock.getWarehouseId(), erpStock.getMaterialId());
            BigDecimal currentQty = current != null ? current.getQuantity() : BigDecimal.ZERO;
            boolean strict = erpStock.getStrict() == null || erpStock.getStrict();
            if (strict && currentQty.add(delta).compareTo(BigDecimal.ZERO) < 0)
            {
                throw new ServiceException("库存不足");
            }
            if (currentQty.add(delta).compareTo(BigDecimal.ZERO) < 0)
            {
                delta = currentQty.negate();
            }
        }
        return stockMapper.insertOrAddStock(erpStock.getWarehouseId(), erpStock.getMaterialId(), delta);
    }
}