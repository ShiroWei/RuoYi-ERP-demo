package com.ruoyi.erp.production.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.production.domain.ErpProductionOrder;
import com.ruoyi.erp.production.mapper.ErpProductionOrderMapper;
import com.ruoyi.erp.production.service.IErpProductionOrderService;

/**
 * 生产工单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpProductionOrderServiceImpl implements IErpProductionOrderService
{
    @Autowired
    private ErpProductionOrderMapper productionOrderMapper;

    /**
     * 查询生产工单
     */
    @Override
    public ErpProductionOrder selectErpProductionOrderById(Long orderId)
    {
        return productionOrderMapper.selectErpProductionOrderById(orderId);
    }

    /**
     * 查询生产工单列表
     */
    @Override
    public List<ErpProductionOrder> selectErpProductionOrderList(ErpProductionOrder erpProductionOrder)
    {
        return productionOrderMapper.selectErpProductionOrderList(erpProductionOrder);
    }

    /**
     * 新增生产工单
     */
    @Override
    public int insertErpProductionOrder(ErpProductionOrder erpProductionOrder)
    {
        erpProductionOrder.setOrderNo(generateOrderNo());
        erpProductionOrder.setCreateBy(SecurityUtils.getUsername());
        erpProductionOrder.setCreateTime(DateUtils.getNowDate());
        return productionOrderMapper.insertErpProductionOrder(erpProductionOrder);
    }

    /**
     * 修改生产工单
     */
    @Override
    public int updateErpProductionOrder(ErpProductionOrder erpProductionOrder)
    {
        erpProductionOrder.setUpdateBy(SecurityUtils.getUsername());
        erpProductionOrder.setUpdateTime(DateUtils.getNowDate());
        return productionOrderMapper.updateErpProductionOrder(erpProductionOrder);
    }

    /**
     * 删除生产工单
     */
    @Override
    public int deleteErpProductionOrderById(Long orderId)
    {
        return productionOrderMapper.deleteErpProductionOrderById(orderId);
    }

    /**
     * 生成工单编号：MO + yyyyMMddHHmmss
     */
    private String generateOrderNo()
    {
        return "MO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}