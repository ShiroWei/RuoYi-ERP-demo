package com.ruoyi.erp.purchase.service;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrder;

/**
 * 采购订单Service接口
 * 
 * @author erp
 */
public interface IErpPurchaseOrderService
{
    /**
     * 查询采购订单
     */
    public ErpPurchaseOrder selectErpPurchaseOrderById(Long orderId);

    /**
     * 查询采购订单列表
     */
    public List<ErpPurchaseOrder> selectErpPurchaseOrderList(ErpPurchaseOrder erpPurchaseOrder);

    /**
     * 新增采购订单
     */
    public int insertErpPurchaseOrder(ErpPurchaseOrder erpPurchaseOrder);

    /**
     * 修改采购订单
     */
    public int updateErpPurchaseOrder(ErpPurchaseOrder erpPurchaseOrder);

    /**
     * 删除采购订单
     */
    public int deleteErpPurchaseOrderById(Long orderId);

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpPurchaseOrder(Long orderId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpPurchaseOrder(Long orderId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpPurchaseOrder(Long orderId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpPurchaseOrder(Long orderId);
}