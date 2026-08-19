package com.ruoyi.erp.stock.service;

import java.util.List;
import com.ruoyi.erp.stock.domain.ErpStockTransfer;

/**
 * 库存调拨Service接口
 * 
 * @author erp
 */
public interface IErpStockTransferService
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

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpStockTransfer(Long transferId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpStockTransfer(Long transferId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpStockTransfer(Long transferId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpStockTransfer(Long transferId);
}