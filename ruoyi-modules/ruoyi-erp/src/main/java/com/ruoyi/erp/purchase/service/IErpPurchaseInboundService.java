package com.ruoyi.erp.purchase.service;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseInbound;

/**
 * 采购入库单Service接口
 * 
 * @author erp
 */
public interface IErpPurchaseInboundService
{
    /**
     * 查询采购入库单
     */
    public ErpPurchaseInbound selectErpPurchaseInboundById(Long inboundId);

    /**
     * 查询采购入库单列表
     */
    public List<ErpPurchaseInbound> selectErpPurchaseInboundList(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 新增采购入库单
     */
    public int insertErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 修改采购入库单
     */
    public int updateErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 删除采购入库单
     */
    public int deleteErpPurchaseInboundById(Long inboundId);

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpPurchaseInbound(Long inboundId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpPurchaseInbound(Long inboundId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpPurchaseInbound(Long inboundId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpPurchaseInbound(Long inboundId);
}