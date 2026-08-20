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
import com.ruoyi.erp.purchase.feign.StockAdjustReq;
import com.ruoyi.erp.purchase.feign.StockFeignClient;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseInboundMapper;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseOrderItemMapper;
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
        erpPurchaseInbound.setUpdateBy(SecurityUtils.getUsername());
        erpPurchaseInbound.setUpdateTime(DateUtils.getNowDate());
        return purchaseInboundMapper.updateErpPurchaseInbound(erpPurchaseInbound);
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