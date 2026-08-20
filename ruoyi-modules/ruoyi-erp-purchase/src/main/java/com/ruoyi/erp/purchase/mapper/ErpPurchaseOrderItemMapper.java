package com.ruoyi.erp.purchase.mapper;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrderItem;

/**
 * 采购订单明细Mapper接口
 * 
 * @author erp
 */
public interface ErpPurchaseOrderItemMapper
{
    /**
     * 查询采购订单明细
     */
    public List<ErpPurchaseOrderItem> selectErpPurchaseOrderItemByOrderId(Long orderId);

    /**
     * 新增采购订单明细
     */
    public int insertErpPurchaseOrderItem(ErpPurchaseOrderItem erpPurchaseOrderItem);

    /**
     * 删除采购订单明细
     */
    public int deleteErpPurchaseOrderItemByOrderId(Long orderId);
}