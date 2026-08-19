package com.ruoyi.erp.production.service;

import java.util.List;
import com.ruoyi.erp.production.domain.ErpProductionOrder;

/**
 * 生产工单Service接口
 * 
 * @author erp
 */
public interface IErpProductionOrderService
{
    /**
     * 查询生产工单
     */
    public ErpProductionOrder selectErpProductionOrderById(Long orderId);

    /**
     * 查询生产工单列表
     */
    public List<ErpProductionOrder> selectErpProductionOrderList(ErpProductionOrder erpProductionOrder);

    /**
     * 新增生产工单
     */
    public int insertErpProductionOrder(ErpProductionOrder erpProductionOrder);

    /**
     * 修改生产工单
     */
    public int updateErpProductionOrder(ErpProductionOrder erpProductionOrder);

    /**
     * 删除生产工单
     */
    public int deleteErpProductionOrderById(Long orderId);
}