package com.ruoyi.erp.purchase.mapper;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrder;

/**
 * 采购订单Mapper接口
 * 
 * @author erp
 */
public interface ErpPurchaseOrderMapper
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
}