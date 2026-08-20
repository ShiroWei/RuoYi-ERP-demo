package com.ruoyi.erp.stock.mapper;

import java.util.List;
import com.ruoyi.erp.stock.domain.ErpStockTransfer;

/**
 * 库存调拨Mapper接口
 * 
 * @author erp
 */
public interface ErpStockTransferMapper
{
    /**
     * 查询库存调拨
     */
    public ErpStockTransfer selectErpStockTransferById(Long transferId);

    /**
     * 查询库存调拨列表
     */
    public List<ErpStockTransfer> selectErpStockTransferList(ErpStockTransfer erpStockTransfer);

    /**
     * 新增库存调拨
     */
    public int insertErpStockTransfer(ErpStockTransfer erpStockTransfer);

    /**
     * 修改库存调拨
     */
    public int updateErpStockTransfer(ErpStockTransfer erpStockTransfer);

    /**
     * 删除库存调拨
     */
    public int deleteErpStockTransferById(Long transferId);
}