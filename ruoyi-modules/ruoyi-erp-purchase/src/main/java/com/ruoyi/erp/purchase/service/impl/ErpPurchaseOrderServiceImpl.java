package com.ruoyi.erp.purchase.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrder;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrderItem;
import com.ruoyi.erp.purchase.feign.FinanceFeignClient;
import com.ruoyi.erp.purchase.feign.PayableReq;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseOrderItemMapper;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseOrderMapper;
import com.ruoyi.erp.purchase.service.IErpPurchaseOrderService;

/**
 * 采购订单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPurchaseOrderServiceImpl implements IErpPurchaseOrderService
{
    @Autowired
    private ErpPurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private ErpPurchaseOrderItemMapper purchaseOrderItemMapper;

    @Autowired
    private FinanceFeignClient financeFeignClient;

    /**
     * 查询采购订单
     */
    @Override
    public ErpPurchaseOrder selectErpPurchaseOrderById(Long orderId)
    {
        ErpPurchaseOrder order = purchaseOrderMapper.selectErpPurchaseOrderById(orderId);
        if (order != null)
        {
            order.setItems(purchaseOrderItemMapper.selectErpPurchaseOrderItemByOrderId(orderId));
        }
        return order;
    }

    /**
     * 查询采购订单列表
     */
    @Override
    public List<ErpPurchaseOrder> selectErpPurchaseOrderList(ErpPurchaseOrder erpPurchaseOrder)
    {
        return purchaseOrderMapper.selectErpPurchaseOrderList(erpPurchaseOrder);
    }

    /**
     * 删除采购订单（仅草稿或已驳回可删）
     */
    @Override
    public int deleteErpPurchaseOrderById(Long orderId)
    {
        ErpPurchaseOrder order = purchaseOrderMapper.selectErpPurchaseOrderById(orderId);
        if (order != null && !"0".equals(order.getStatus()) && !"3".equals(order.getStatus()))
        {
            throw new ServiceException("仅草稿或已驳回单据可删除");
        }
        purchaseOrderItemMapper.deleteErpPurchaseOrderItemByOrderId(orderId);
        return purchaseOrderMapper.deleteErpPurchaseOrderById(orderId);
    }

    /**
     * 新增采购订单（主表 + 明细行）
     */
    @Override
    @Transactional
    public int insertErpPurchaseOrder(ErpPurchaseOrder erpPurchaseOrder)
    {
        erpPurchaseOrder.setOrderNo(generateOrderNo());
        erpPurchaseOrder.setStatus("0");
        erpPurchaseOrder.setCreateBy(SecurityUtils.getUsername());
        erpPurchaseOrder.setCreateTime(DateUtils.getNowDate());
        int result = purchaseOrderMapper.insertErpPurchaseOrder(erpPurchaseOrder);
        if (erpPurchaseOrder.getItems() != null)
        {
            for (ErpPurchaseOrderItem item : erpPurchaseOrder.getItems())
            {
                item.setOrderId(erpPurchaseOrder.getOrderId());
                purchaseOrderItemMapper.insertErpPurchaseOrderItem(item);
            }
        }
        return result;
    }

    /**
     * 修改采购订单（重建明细行）
     */
    @Override
    @Transactional
    public int updateErpPurchaseOrder(ErpPurchaseOrder erpPurchaseOrder)
    {
        erpPurchaseOrder.setUpdateBy(SecurityUtils.getUsername());
        erpPurchaseOrder.setUpdateTime(DateUtils.getNowDate());
        int result = purchaseOrderMapper.updateErpPurchaseOrder(erpPurchaseOrder);
        purchaseOrderItemMapper.deleteErpPurchaseOrderItemByOrderId(erpPurchaseOrder.getOrderId());
        if (erpPurchaseOrder.getItems() != null)
        {
            for (ErpPurchaseOrderItem item : erpPurchaseOrder.getItems())
            {
                item.setOrderId(erpPurchaseOrder.getOrderId());
                purchaseOrderItemMapper.insertErpPurchaseOrderItem(item);
            }
        }
        return result;
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpPurchaseOrder(Long orderId)
    {
        return updateStatus(orderId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpPurchaseOrder(Long orderId)
    {
        return updateStatus(orderId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpPurchaseOrder(Long orderId)
    {
        return updateStatus(orderId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）+ 自动生成应付账款（调用财务服务）
     */
    @Override
    public int completeErpPurchaseOrder(Long orderId)
    {
        ErpPurchaseOrder order = purchaseOrderMapper.selectErpPurchaseOrderById(orderId);
        if ("4".equals(order.getStatus()))
        {
            return 1;
        }
        if (order.getSupplierId() != null && order.getSupplierId() > 0)
        {
            PayableReq req = new PayableReq();
            req.setBillNo(order.getOrderNo());
            req.setBillType("采购订单");
            req.setSupplierId(order.getSupplierId());
            req.setAmount(order.getTotalAmount());
            req.setDueDate(order.getOrderDate());
            com.ruoyi.common.core.web.domain.AjaxResult result = financeFeignClient.createPayable(req);
            if (result == null || !result.isSuccess())
            {
                throw new RuntimeException("生成应付账款失败");
            }
        }
        return updateStatus(orderId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long orderId, String status)
    {
        ErpPurchaseOrder order = new ErpPurchaseOrder();
        order.setOrderId(orderId);
        order.setStatus(status);
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(DateUtils.getNowDate());
        return purchaseOrderMapper.updateErpPurchaseOrder(order);
    }

    /**
     * 生成订单编号：PO + yyyyMMddHHmmss
     */
    private String generateOrderNo()
    {
        return "PO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}