package com.ruoyi.erp.stock.service;

import java.util.List;
import com.ruoyi.erp.stock.domain.ErpStock;

/**
 * 库存Service接口
 * 
 * @author erp
 */
public interface IErpStockService
{
    /**
     * 查询库存
     */
    public ErpStock selectErpStockById(Long stockId);

    /**
     * 查询库存列表
     */
    public List<ErpStock> selectErpStockList(ErpStock erpStock);

    /**
     * 新增库存
     */
    public int insertErpStock(ErpStock erpStock);

    /**
     * 修改库存
     */
    public int updateErpStock(ErpStock erpStock);

    /**
     * 删除库存
     */
    public int deleteErpStockById(Long stockId);
}