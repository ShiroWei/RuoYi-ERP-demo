package com.ruoyi.erp.sale.mapper;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleOrder;

/**
 * 销售订单Mapper接口
 * 
 * @author erp
 */
public interface ErpSaleOrderMapper
{
    /**
     * 查询销售订单
     */
    public ErpSaleOrder selectErpSaleOrderById(Long orderId);

    /**
     * 查询销售订单列表
     */
    public List<ErpSaleOrder> selectErpSaleOrderList(ErpSaleOrder ErpSaleOrder);

    /**
     * 新增销售订单
     */
    public int insertErpSaleOrder(ErpSaleOrder ErpSaleOrder);

    /**
     * 修改销售订单
     */
    public int updateErpSaleOrder(ErpSaleOrder ErpSaleOrder);

    /**
     * 删除销售订单
     */
    public int deleteErpSaleOrderById(Long orderId);
}