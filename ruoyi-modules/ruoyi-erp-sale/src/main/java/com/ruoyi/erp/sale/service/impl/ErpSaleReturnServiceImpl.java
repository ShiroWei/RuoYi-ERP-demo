package com.ruoyi.erp.sale.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.sale.domain.ErpSaleOrderItem;
import com.ruoyi.erp.sale.domain.ErpSaleOutbound;
import com.ruoyi.erp.sale.domain.ErpSaleOrder;
import com.ruoyi.erp.sale.domain.ErpSaleReturn;
import com.ruoyi.erp.sale.feign.StockAdjustReq;
import com.ruoyi.erp.sale.feign.StockFeignClient;
import com.ruoyi.erp.sale.mapper.ErpSaleOrderItemMapper;
import com.ruoyi.erp.sale.mapper.ErpSaleOutboundMapper;
import com.ruoyi.erp.sale.mapper.ErpSaleOrderMapper;
import com.ruoyi.erp.sale.mapper.ErpSaleReturnMapper;
import com.ruoyi.erp.sale.service.IErpSaleReturnService;

/**
 * 销售退货单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpSaleReturnServiceImpl implements IErpSaleReturnService
{
    @Autowired
    private ErpSaleReturnMapper saleReturnMapper;

    @Autowired
    private ErpSaleOrderItemMapper saleOrderItemMapper;

    @Autowired
    private ErpSaleOutboundMapper saleOutboundMapper;

    @Autowired
    private ErpSaleOrderMapper saleOrderMapper;

    @Autowired
    private StockFeignClient stockFeignClient;

    /**
     * 查询销售退货单
     */
    @Override
    public ErpSaleReturn selectErpSaleReturnById(Long returnId)
    {
        return saleReturnMapper.selectErpSaleReturnById(returnId);
    }

    /**
     * 查询销售退货单列表
     */
    @Override
    public List<ErpSaleReturn> selectErpSaleReturnList(ErpSaleReturn ErpSaleReturn)
    {
        return saleReturnMapper.selectErpSaleReturnList(ErpSaleReturn);
    }

    /**
     * 新增销售退货单
     */
    @Override
    public int insertErpSaleReturn(ErpSaleReturn ErpSaleReturn)
    {
        fillAndValidateSource(ErpSaleReturn);
        validateOrderNotReturned(ErpSaleReturn);
        ErpSaleReturn.setReturnNo(generateReturnNo());
        ErpSaleReturn.setStatus("0");
        ErpSaleReturn.setCreateBy(SecurityUtils.getUsername());
        ErpSaleReturn.setCreateTime(DateUtils.getNowDate());
        return saleReturnMapper.insertErpSaleReturn(ErpSaleReturn);
    }

    /**
     * 修改销售退货单
     */
    @Override
    public int updateErpSaleReturn(ErpSaleReturn ErpSaleReturn)
    {
        fillAndValidateSource(ErpSaleReturn);
        validateOrderNotReturned(ErpSaleReturn);
        ErpSaleReturn.setUpdateBy(SecurityUtils.getUsername());
        ErpSaleReturn.setUpdateTime(DateUtils.getNowDate());
        return saleReturnMapper.updateErpSaleReturn(ErpSaleReturn);
    }

    private void fillAndValidateSource(ErpSaleReturn returnOrder)
    {
        if (returnOrder.getOrderId() == null || returnOrder.getOrderId() <= 0)
        {
            throw new ServiceException("请选择来源销售出库单");
        }
        ErpSaleOutbound query = new ErpSaleOutbound();
        query.setOrderId(returnOrder.getOrderId());
        query.setStatus("4");
        List<ErpSaleOutbound> outbounds = saleOutboundMapper.selectErpSaleOutboundList(query);
        if (outbounds == null || outbounds.isEmpty())
        {
            throw new ServiceException("关联销售订单尚未完成出库，不能退货");
        }
        ErpSaleOrder order = saleOrderMapper.selectErpSaleOrderById(returnOrder.getOrderId());
        ErpSaleOutbound outbound = outbounds.get(0);
        returnOrder.setCustomerId(order.getCustomerId());
        returnOrder.setWarehouseId(outbound.getWarehouseId());
        returnOrder.setTotalAmount(outbound.getTotalAmount());
    }

    private void validateOrderNotReturned(ErpSaleReturn returnOrder)
    {
        ErpSaleReturn query = new ErpSaleReturn();
        query.setOrderId(returnOrder.getOrderId());
        List<ErpSaleReturn> exists = saleReturnMapper.selectErpSaleReturnList(query);
        for (ErpSaleReturn item : exists)
        {
            if (returnOrder.getReturnId() == null || !returnOrder.getReturnId().equals(item.getReturnId()))
            {
                throw new ServiceException("该销售订单已经关联退货单，不能重复退货");
            }
        }
    }

    /**
     * 删除销售退货单（仅草稿或已驳回可删）
     */
    @Override
    public int deleteErpSaleReturnById(Long returnId)
    {
        ErpSaleReturn returnOrder = saleReturnMapper.selectErpSaleReturnById(returnId);
        if (returnOrder != null && !"0".equals(returnOrder.getStatus()) && !"3".equals(returnOrder.getStatus()))
        {
            throw new ServiceException("仅草稿或已驳回单据可删除");
        }
        return saleReturnMapper.deleteErpSaleReturnById(returnId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）+ 库存联动（退货加库存）
     */
    @Override
    public int completeErpSaleReturn(Long returnId)
    {
        ErpSaleReturn returnOrder = saleReturnMapper.selectErpSaleReturnById(returnId);
        if ("4".equals(returnOrder.getStatus()))
        {
            return 1;
        }
        if (returnOrder.getOrderId() != null && returnOrder.getOrderId() > 0)
        {
            List<ErpSaleOrderItem> items = saleOrderItemMapper.selectErpSaleOrderItemByOrderId(returnOrder.getOrderId());
            for (ErpSaleOrderItem item : items)
            {
                StockAdjustReq req = new StockAdjustReq();
                req.setWarehouseId(returnOrder.getWarehouseId());
                req.setMaterialId(item.getMaterialId());
                req.setQuantity(item.getQuantity());
                req.setStrict(Boolean.FALSE);
                com.ruoyi.common.core.web.domain.AjaxResult result = stockFeignClient.adjust(req);
                if (result == null || !result.isSuccess())
                {
                    throw new RuntimeException("库存联动失败");
                }
            }
        }
        return updateStatus(returnId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long returnId, String status)
    {
        ErpSaleReturn ret = new ErpSaleReturn();
        ret.setReturnId(returnId);
        ret.setStatus(status);
        ret.setUpdateBy(SecurityUtils.getUsername());
        ret.setUpdateTime(DateUtils.getNowDate());
        return saleReturnMapper.updateErpSaleReturn(ret);
    }

    /**
     * 生成退货单号：RTPO + yyyyMMddHHmmss
     */
    private String generateReturnNo()
    {
        return "RTSO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}
