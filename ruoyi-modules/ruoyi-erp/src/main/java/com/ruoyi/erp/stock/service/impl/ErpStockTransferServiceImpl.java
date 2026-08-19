package com.ruoyi.erp.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.stock.domain.ErpStockTransfer;
import com.ruoyi.erp.stock.mapper.ErpStockTransferMapper;
import com.ruoyi.erp.stock.service.IErpStockTransferService;

/**
 * 库存调拨Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpStockTransferServiceImpl implements IErpStockTransferService
{
    @Autowired
    private ErpStockTransferMapper stockTransferMapper;

    /**
     * 查询库存调拨
     */
    @Override
    public ErpStockTransfer selectErpStockTransferById(Long transferId)
    {
        return stockTransferMapper.selectErpStockTransferById(transferId);
    }

    /**
     * 查询库存调拨列表
     */
    @Override
    public List<ErpStockTransfer> selectErpStockTransferList(ErpStockTransfer erpStockTransfer)
    {
        return stockTransferMapper.selectErpStockTransferList(erpStockTransfer);
    }

    /**
     * 新增库存调拨
     */
    @Override
    public int insertErpStockTransfer(ErpStockTransfer erpStockTransfer)
    {
        erpStockTransfer.setTransferNo(generateTransferNo());
        erpStockTransfer.setStatus("0");
        erpStockTransfer.setCreateBy(SecurityUtils.getUsername());
        erpStockTransfer.setCreateTime(DateUtils.getNowDate());
        return stockTransferMapper.insertErpStockTransfer(erpStockTransfer);
    }

    /**
     * 修改库存调拨
     */
    @Override
    public int updateErpStockTransfer(ErpStockTransfer erpStockTransfer)
    {
        erpStockTransfer.setUpdateBy(SecurityUtils.getUsername());
        erpStockTransfer.setUpdateTime(DateUtils.getNowDate());
        return stockTransferMapper.updateErpStockTransfer(erpStockTransfer);
    }

    /**
     * 删除库存调拨
     */
    @Override
    public int deleteErpStockTransferById(Long transferId)
    {
        return stockTransferMapper.deleteErpStockTransferById(transferId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpStockTransfer(Long transferId)
    {
        return updateStatus(transferId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpStockTransfer(Long transferId)
    {
        return updateStatus(transferId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpStockTransfer(Long transferId)
    {
        return updateStatus(transferId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpStockTransfer(Long transferId)
    {
        return updateStatus(transferId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long transferId, String status)
    {
        ErpStockTransfer transfer = new ErpStockTransfer();
        transfer.setTransferId(transferId);
        transfer.setStatus(status);
        transfer.setUpdateBy(SecurityUtils.getUsername());
        transfer.setUpdateTime(DateUtils.getNowDate());
        return stockTransferMapper.updateErpStockTransfer(transfer);
    }

    /**
     * 生成调拨单号：TF + yyyyMMddHHmmss
     */
    private String generateTransferNo()
    {
        return "TF" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}