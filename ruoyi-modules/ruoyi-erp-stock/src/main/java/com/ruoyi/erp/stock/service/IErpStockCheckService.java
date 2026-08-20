package com.ruoyi.erp.stock.service;

import java.util.List;
import com.ruoyi.erp.stock.domain.ErpStockCheck;

/**
 * 库存盘点Service接口
 * 
 * @author erp
 */
public interface IErpStockCheckService
{
    /**
     * 查询库存盘点
     */
    public ErpStockCheck selectErpStockCheckById(Long checkId);

    /**
     * 查询库存盘点列表
     */
    public List<ErpStockCheck> selectErpStockCheckList(ErpStockCheck erpStockCheck);

    /**
     * 新增库存盘点
     */
    public int insertErpStockCheck(ErpStockCheck erpStockCheck);

    /**
     * 修改库存盘点
     */
    public int updateErpStockCheck(ErpStockCheck erpStockCheck);

    /**
     * 删除库存盘点
     */
    public int deleteErpStockCheckById(Long checkId);
}