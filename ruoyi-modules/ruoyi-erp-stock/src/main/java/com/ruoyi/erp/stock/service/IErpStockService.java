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

    /**
     * 调整库存数量（供其它服务 Feign 调用）：quantity 为正则增加、为负则减少
     */
    public int adjustStock(ErpStock erpStock);
}