package com.ruoyi.erp.sale.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.sale.domain.ErpSaleOrder;
import com.ruoyi.erp.sale.domain.ErpSaleOrderItem;
import com.ruoyi.erp.sale.feign.FinanceFeignClient;
import com.ruoyi.erp.sale.feign.ReceivableReq;
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

    @Autowired
    private FinanceFeignClient financeFeignClient;

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
    public List<ErpSaleOrder> selectErpSaleOrderList(ErpSaleOrder erpSaleOrder)
    {
        return saleOrderMapper.selectErpSaleOrderList(erpSaleOrder);
    }

    /**
     * 删除销售订单（仅草稿或已驳回可删）
     */
    @Override
    public int deleteErpSaleOrderById(Long orderId)
    {
        ErpSaleOrder order = saleOrderMapper.selectErpSaleOrderById(orderId);
        if (order != null && !"0".equals(order.getStatus()) && !"3".equals(order.getStatus()))
        {
            throw new ServiceException("仅草稿或已驳回单据可删除");
        }
        saleOrderItemMapper.deleteErpSaleOrderItemByOrderId(orderId);
        return saleOrderMapper.deleteErpSaleOrderById(orderId);
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
     * 完成（审核通过 -> 已完成）+ 自动生成应收账款（调用财务服务）
     */
    @Override
    public int completeErpSaleOrder(Long orderId)
    {
        ErpSaleOrder order = saleOrderMapper.selectErpSaleOrderById(orderId);
        if ("4".equals(order.getStatus()))
        {
            return 1;
        }
        if (order.getCustomerId() != null && order.getCustomerId() > 0)
        {
            ReceivableReq req = new ReceivableReq();
            req.setBillNo(order.getOrderNo());
            req.setBillType("销售订单");
            req.setCustomerId(order.getCustomerId());
            req.setAmount(order.getTotalAmount());
            req.setDueDate(order.getOrderDate());
            com.ruoyi.common.core.web.domain.AjaxResult result = financeFeignClient.createReceivable(req);
            if (result == null || !result.isSuccess())
            {
                throw new RuntimeException("生成应收账款失败");
            }
        }
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