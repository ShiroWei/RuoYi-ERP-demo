package com.ruoyi.erp.sale.service;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleOrder;

/**
 * 销售订单Service接口
 * 
 * @author erp
 */
public interface IErpSaleOrderService
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

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpSaleOrder(Long orderId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpSaleOrder(Long orderId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpSaleOrder(Long orderId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpSaleOrder(Long orderId);
}