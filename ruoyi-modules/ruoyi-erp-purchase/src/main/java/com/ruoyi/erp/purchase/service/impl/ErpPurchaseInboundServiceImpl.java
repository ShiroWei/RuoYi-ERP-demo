package com.ruoyi.erp.purchase.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.purchase.domain.ErpPurchaseInbound;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrderItem;
import com.ruoyi.erp.purchase.domain.ErpPurchaseOrder;
import com.ruoyi.erp.purchase.feign.StockAdjustReq;
import com.ruoyi.erp.purchase.feign.StockFeignClient;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseInboundMapper;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseOrderItemMapper;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseOrderMapper;
import com.ruoyi.erp.purchase.service.IErpPurchaseInboundService;

/**
 * 采购入库单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPurchaseInboundServiceImpl implements IErpPurchaseInboundService
{
    @Autowired
    private ErpPurchaseInboundMapper purchaseInboundMapper;

    @Autowired
    private ErpPurchaseOrderItemMapper purchaseOrderItemMapper;

    @Autowired
    private ErpPurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private StockFeignClient stockFeignClient;

    /**
     * 查询采购入库单
     */
    @Override
    public ErpPurchaseInbound selectErpPurchaseInboundById(Long inboundId)
    {
        return purchaseInboundMapper.selectErpPurchaseInboundById(inboundId);
    }

    /**
     * 查询采购入库单列表
     */
    @Override
    public List<ErpPurchaseInbound> selectErpPurchaseInboundList(ErpPurchaseInbound erpPurchaseInbound)
    {
        return purchaseInboundMapper.selectErpPurchaseInboundList(erpPurchaseInbound);
    }

    /**
     * 新增采购入库单
     */
    @Override
    public int insertErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound)
    {
        fillAndValidateOrder(erpPurchaseInbound);
        validateOrderNotInbound(erpPurchaseInbound);
        erpPurchaseInbound.setInboundNo(generateInboundNo());
        erpPurchaseInbound.setStatus("0");
        erpPurchaseInbound.setCreateBy(SecurityUtils.getUsername());
        erpPurchaseInbound.setCreateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.insertErpPurchaseInbound(erpPurchaseInbound);
    }

    /**
     * 修改采购入库单
     */
    @Override
    public int updateErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound)
    {
        fillAndValidateOrder(erpPurchaseInbound);
        validateOrderNotInbound(erpPurchaseInbound);
        erpPurchaseInbound.setUpdateBy(SecurityUtils.getUsername());
        erpPurchaseInbound.setUpdateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.updateErpPurchaseInbound(erpPurchaseInbound);
    }

    /**
     * 校验关联采购订单，并以订单数据回填供应商和金额，避免入库单与订单不一致。
     */
    private void fillAndValidateOrder(ErpPurchaseInbound inbound)
    {
        if (inbound.getOrderId() == null || inbound.getOrderId() <= 0)
        {
            throw new ServiceException("请选择关联采购订单");
        }
        ErpPurchaseOrder order = purchaseOrderMapper.selectErpPurchaseOrderById(inbound.getOrderId());
        if (order == null)
        {
            throw new ServiceException("关联采购订单不存在");
        }
        if (!"2".equals(order.getStatus()) && !"4".equals(order.getStatus()))
        {
            throw new ServiceException("只能关联审核通过或已完成的采购订单");
        }
        inbound.setSupplierId(order.getSupplierId());
        inbound.setTotalAmount(order.getTotalAmount());
    }

    /**
     * 当前入库单没有明细行，只支持整单入库，因此同一采购订单只能创建一张入库单。
     */
    private void validateOrderNotInbound(ErpPurchaseInbound inbound)
    {
        ErpPurchaseInbound query = new ErpPurchaseInbound();
        query.setOrderId(inbound.getOrderId());
        List<ErpPurchaseInbound> exists = purchaseInboundMapper.selectErpPurchaseInboundList(query);
        for (ErpPurchaseInbound item : exists)
        {
            if (inbound.getInboundId() == null || !inbound.getInboundId().equals(item.getInboundId()))
            {
                throw new ServiceException("该采购订单已经关联入库单，不能重复入库");
            }
        }
    }

    /**
     * 删除采购入库单（仅草稿或已驳回可删）
     */
    @Override
    public int deleteErpPurchaseInboundById(Long inboundId)
    {
        ErpPurchaseInbound inbound = purchaseInboundMapper.selectErpPurchaseInboundById(inboundId);
        if (inbound != null && !"0".equals(inbound.getStatus()) && !"3".equals(inbound.getStatus()))
        {
            throw new ServiceException("仅草稿或已驳回单据可删除");
        }
        return purchaseInboundMapper.deleteErpPurchaseInboundById(inboundId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpPurchaseInbound(Long inboundId)
    {
        return updateStatus(inboundId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）+ 库存联动（调用库存服务）
     */
    @Override
    public int completeErpPurchaseInbound(Long inboundId)
    {
        ErpPurchaseInbound inbound = purchaseInboundMapper.selectErpPurchaseInboundById(inboundId);
        if ("4".equals(inbound.getStatus()))
        {
            return 1;
        }
        if (inbound.getOrderId() != null && inbound.getOrderId() > 0)
        {
            List<ErpPurchaseOrderItem> items = purchaseOrderItemMapper.selectErpPurchaseOrderItemByOrderId(inbound.getOrderId());
            for (ErpPurchaseOrderItem item : items)
            {
                StockAdjustReq req = new StockAdjustReq();
                req.setWarehouseId(inbound.getWarehouseId());
                req.setMaterialId(item.getMaterialId());
                req.setQuantity(item.getQuantity());
                req.setStrict(Boolean.FALSE);
                checkResult(stockFeignClient.adjust(req));
            }
        }
        return updateStatus(inboundId, "4");
    }

    /**
     * 校验联动结果
     */
    private void checkResult(com.ruoyi.common.core.web.domain.AjaxResult result)
    {
        if (result == null || !result.isSuccess())
        {
            throw new RuntimeException("库存联动失败");
        }
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long inboundId, String status)
    {
        ErpPurchaseInbound inbound = new ErpPurchaseInbound();
        inbound.setInboundId(inboundId);
        inbound.setStatus(status);
        inbound.setUpdateBy(SecurityUtils.getUsername());
        inbound.setUpdateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.updateErpPurchaseInbound(inbound);
    }

    /**
     * 生成入库单号：IN + yyyyMMddHHmmss
     */
    private String generateInboundNo()
    {
        return "IN" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}
