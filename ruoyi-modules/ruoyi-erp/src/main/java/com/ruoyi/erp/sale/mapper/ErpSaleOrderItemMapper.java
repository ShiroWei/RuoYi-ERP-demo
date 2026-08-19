package com.ruoyi.erp.sale.mapper;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleOrderItem;

/**
 * 销售订单明细Mapper接口
 * 
 * @author erp
 */
public interface ErpSaleOrderItemMapper
{
    /**
     * 查询销售订单明细
     */
    public List<ErpSaleOrderItem> selectErpSaleOrderItemByOrderId(Long orderId);

    /**
     * 新增销售订单明细
     */
    public int insertErpSaleOrderItem(ErpSaleOrderItem ErpSaleOrderItem);

    /**
     * 删除销售订单明细
     */
    public int deleteErpSaleOrderItemByOrderId(Long orderId);
}