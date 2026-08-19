package com.ruoyi.erp.sale.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.sale.domain.ErpSaleOrder;
import com.ruoyi.erp.sale.domain.ErpSaleOrderItem;
import com.ruoyi.erp.sale.mapper.ErpSaleOrderItemMapper;
import com.ruoyi.erp.sale.mapper.ErpSaleOrderMapper;
import com.ruoyi.erp.sale.service.IErpSaleOrderService;

/**
 * 销售订单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpSaleOrderServiceImpl implements IErpSaleOrderService
{
    @Autowired
    private ErpSaleOrderMapper saleOrderMapper;

    @Autowired
    private ErpSaleOrderItemMapper saleOrderItemMapper;

    /**
     * 查询销售订单
     */
    @Override
    public ErpSaleOrder selectErpSaleOrderById(Long orderId)
    {
        ErpSaleOrder order = saleOrderMapper.selectErpSaleOrderById(orderId);
        if (order != null)
        {
            order.setItems(saleOrderItemMapper.selectErpSaleOrderItemByOrderId(orderId));
        }
        return order;
    }

    /**
     * 查询销售订单列表
     */
    @Override
    public List<ErpSaleOrder> selectErpSaleOrderList(ErpSaleOrder ErpSaleOrder)
    {
        return saleOrderMapper.selectErpSaleOrderList(ErpSaleOrder);
    }

    /**
     * 新增销售订单（主表 + 明细行）
     */
    @Override
    @Transactional
    public int insertErpSaleOrder(ErpSaleOrder ErpSaleOrder)
    {
        ErpSaleOrder.setOrderNo(generateOrderNo());
        ErpSaleOrder.setStatus("0");
        ErpSaleOrder.setCreateBy(SecurityUtils.getUsername());
        ErpSaleOrder.setCreateTime(DateUtils.getNowDate());
        int result = saleOrderMapper.insertErpSaleOrder(ErpSaleOrder);
        if (ErpSaleOrder.getItems() != null)
        {
            for (ErpSaleOrderItem item : ErpSaleOrder.getItems())
            {
                item.setOrderId(ErpSaleOrder.getOrderId());
                saleOrderItemMapper.insertErpSaleOrderItem(item);
            }
        }
        return result;
    }

    /**
     * 修改销售订单（重建明细行）
     */
    @Override
    @Transactional
    public int updateErpSaleOrder(ErpSaleOrder ErpSaleOrder)
    {
        ErpSaleOrder.setUpdateBy(SecurityUtils.getUsername());
        ErpSaleOrder.setUpdateTime(DateUtils.getNowDate());
        int result = saleOrderMapper.updateErpSaleOrder(ErpSaleOrder);
        saleOrderItemMapper.deleteErpSaleOrderItemByOrderId(ErpSaleOrder.getOrderId());
        if (ErpSaleOrder.getItems() != null)
        {
            for (ErpSaleOrderItem item : ErpSaleOrder.getItems())
            {
                item.setOrderId(ErpSaleOrder.getOrderId());
                saleOrderItemMapper.insertErpSaleOrderItem(item);
            }
        }
        return result;
    }

    /**
     * 删除销售订单
     */
    @Override
    @Transactional
    public int deleteErpSaleOrderById(Long orderId)
    {
        saleOrderItemMapper.deleteErpSaleOrderItemByOrderId(orderId);
        return saleOrderMapper.deleteErpSaleOrderById(orderId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpSaleOrder(Long orderId)
    {
        return updateStatus(orderId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpSaleOrder(Long orderId)
    {
        return updateStatus(orderId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpSaleOrder(Long orderId)
    {
        return updateStatus(orderId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpSaleOrder(Long orderId)
    {
        return updateStatus(orderId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long orderId, String status)
    {
        ErpSaleOrder order = new ErpSaleOrder();
        order.setOrderId(orderId);
        order.setStatus(status);
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(DateUtils.getNowDate());
        return saleOrderMapper.updateErpSaleOrder(order);
    }

    /**
     * 生成订单编号：PO + yyyyMMddHHmmss
     */
    private String generateOrderNo()
    {
        return "SO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}