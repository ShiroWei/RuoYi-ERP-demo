package com.ruoyi.erp.sale.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.sale.domain.ErpSaleOutbound;
import com.ruoyi.erp.sale.domain.ErpSaleOrderItem;
import com.ruoyi.erp.sale.mapper.ErpSaleOutboundMapper;
import com.ruoyi.erp.sale.mapper.ErpSaleOrderItemMapper;
import com.ruoyi.erp.sale.service.IErpSaleOutboundService;
import com.ruoyi.erp.stock.domain.ErpStock;
import com.ruoyi.erp.stock.mapper.ErpStockMapper;

/**
 * 销售出库单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpSaleOutboundServiceImpl implements IErpSaleOutboundService
{
    @Autowired
    private ErpSaleOutboundMapper saleOutboundMapper;

    @Autowired
    private ErpSaleOrderItemMapper saleOrderItemMapper;

    @Autowired
    private ErpStockMapper stockMapper;

    /**
     * 查询销售出库单
     */
    @Override
    public ErpSaleOutbound selectErpSaleOutboundById(Long outboundId)
    {
        return saleOutboundMapper.selectErpSaleOutboundById(outboundId);
    }

    /**
     * 查询销售出库单列表
     */
    @Override
    public List<ErpSaleOutbound> selectErpSaleOutboundList(ErpSaleOutbound ErpSaleOutbound)
    {
        return saleOutboundMapper.selectErpSaleOutboundList(ErpSaleOutbound);
    }

    /**
     * 新增销售出库单
     */
    @Override
    public int insertErpSaleOutbound(ErpSaleOutbound ErpSaleOutbound)
    {
        ErpSaleOutbound.setOutboundNo(generateOutboundNo());
        ErpSaleOutbound.setStatus("0");
        ErpSaleOutbound.setCreateBy(SecurityUtils.getUsername());
        ErpSaleOutbound.setCreateTime(DateUtils.getNowDate());
        return saleOutboundMapper.insertErpSaleOutbound(ErpSaleOutbound);
    }

    /**
     * 修改销售出库单
     */
    @Override
    public int updateErpSaleOutbound(ErpSaleOutbound ErpSaleOutbound)
    {
        ErpSaleOutbound.setUpdateBy(SecurityUtils.getUsername());
        ErpSaleOutbound.setUpdateTime(DateUtils.getNowDate());
        return saleOutboundMapper.updateErpSaleOutbound(ErpSaleOutbound);
    }

    /**
     * 删除销售出库单
     */
    @Override
    public int deleteErpSaleOutboundById(Long outboundId)
    {
        return saleOutboundMapper.deleteErpSaleOutboundById(outboundId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpSaleOutbound(Long outboundId)
    {
        return updateStatus(outboundId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpSaleOutbound(Long outboundId)
    {
        return updateStatus(outboundId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpSaleOutbound(Long outboundId)
    {
        return updateStatus(outboundId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）+ 库存联动
     */
    @Override
    @Transactional
    public int completeErpSaleOutbound(Long outboundId)
    {
        ErpSaleOutbound outbound = saleOutboundMapper.selectErpSaleOutboundById(outboundId);
        if ("4".equals(outbound.getStatus()))
        {
            return 1;
        }
        int rows = updateStatus(outboundId, "4");
        if (rows > 0 && outbound.getOrderId() != null && outbound.getOrderId() > 0)
        {
            List<ErpSaleOrderItem> items = saleOrderItemMapper.selectErpSaleOrderItemByOrderId(outbound.getOrderId());
            for (ErpSaleOrderItem item : items)
            {
                ErpStock stock = stockMapper.selectErpStockByWarehouseAndMaterial(outbound.getWarehouseId(), item.getMaterialId());
                BigDecimal currentQty = stock != null ? stock.getQuantity() : BigDecimal.ZERO;
                if (currentQty.compareTo(item.getQuantity()) < 0)
                {
                    throw new RuntimeException("库存不足");
                }
                stockMapper.insertOrAddStock(outbound.getWarehouseId(), item.getMaterialId(), item.getQuantity().negate());
            }
        }
        return rows;
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long outboundId, String status)
    {
        ErpSaleOutbound Outbound = new ErpSaleOutbound();
        Outbound.setOutboundId(outboundId);
        Outbound.setStatus(status);
        Outbound.setUpdateBy(SecurityUtils.getUsername());
        Outbound.setUpdateTime(DateUtils.getNowDate());
        return saleOutboundMapper.updateErpSaleOutbound(Outbound);
    }

    /**
     * 生成入库单号：IN + yyyyMMddHHmmss
     */
    private String generateOutboundNo()
    {
        return "OUT" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}