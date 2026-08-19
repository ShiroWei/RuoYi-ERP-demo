package com.ruoyi.erp.purchase.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.purchase.domain.ErpPurchaseInbound;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseInboundMapper;
import com.ruoyi.erp.purchase.service.IErpPurchaseInboundService;

/**
 * 采购入库单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPurchaseInboundServiceImpl implements IErpPurchaseInboundService
{
    @Autowired
    private ErpPurchaseInboundMapper purchaseInboundMapper;

    /**
     * 查询采购入库单
     */
    @Override
    public ErpPurchaseInbound selectErpPurchaseInboundById(Long inboundId)
    {
        return purchaseInboundMapper.selectErpPurchaseInboundById(inboundId);
    }

    /**
     * 查询采购入库单列表
     */
    @Override
    public List<ErpPurchaseInbound> selectErpPurchaseInboundList(ErpPurchaseInbound erpPurchaseInbound)
    {
        return purchaseInboundMapper.selectErpPurchaseInboundList(erpPurchaseInbound);
    }

    /**
     * 新增采购入库单
     */
    @Override
    public int insertErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound)
    {
        erpPurchaseInbound.setInboundNo(generateInboundNo());
        erpPurchaseInbound.setStatus("0");
        erpPurchaseInbound.setCreateBy(SecurityUtils.getUsername());
        erpPurchaseInbound.setCreateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.insertErpPurchaseInbound(erpPurchaseInbound);
    }

    /**
     * 修改采购入库单
     */
    @Override
    public int updateErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound)
    {
        erpPurchaseInbound.setUpdateBy(SecurityUtils.getUsername());
        erpPurchaseInbound.setUpdateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.updateErpPurchaseInbound(erpPurchaseInbound);
    }

    /**
     * 删除采购入库单
     */
    @Override
    public int deleteErpPurchaseInboundById(Long inboundId)
    {
        return purchaseInboundMapper.deleteErpPurchaseInboundById(inboundId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long inboundId, String status)
    {
        ErpPurchaseInbound inbound = new ErpPurchaseInbound();
        inbound.setInboundId(inboundId);
        inbound.setStatus(status);
        inbound.setUpdateBy(SecurityUtils.getUsername());
        inbound.setUpdateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.updateErpPurchaseInbound(inbound);
    }

    /**
     * 生成入库单号：IN + yyyyMMddHHmmss
     */
    private String generateInboundNo()
    {
        return "IN" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}