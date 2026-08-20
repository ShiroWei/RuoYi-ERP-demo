package com.ruoyi.erp.production.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.production.domain.ErpBom;
import com.ruoyi.erp.production.domain.ErpBomItem;
import com.ruoyi.erp.production.domain.ErpProductionOrder;
import com.ruoyi.erp.production.feign.StockAdjustReq;
import com.ruoyi.erp.production.feign.StockFeignClient;
import com.ruoyi.erp.production.mapper.ErpBomItemMapper;
import com.ruoyi.erp.production.mapper.ErpBomMapper;
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
    /** 演示用：领料仓库（原料仓） */
    private static final Long PRODUCTION_WAREHOUSE = 1L;

    /** 演示用：完工入库仓库（成品仓） */
    private static final Long FINISHED_WAREHOUSE = 3L;

    @Autowired
    private ErpProductionOrderMapper productionOrderMapper;

    @Autowired
    private ErpBomMapper bomMapper;

    @Autowired
    private ErpBomItemMapper bomItemMapper;

    @Autowired
    private StockFeignClient stockFeignClient;

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
     * 修改生产工单（更新为已完工时触发 BOM 领料与成品入库联动）
     */
    @Override
    public int updateErpProductionOrder(ErpProductionOrder erpProductionOrder)
    {
        ErpProductionOrder old = productionOrderMapper.selectErpProductionOrderById(erpProductionOrder.getOrderId());
        erpProductionOrder.setUpdateBy(SecurityUtils.getUsername());
        erpProductionOrder.setUpdateTime(DateUtils.getNowDate());
        int rows = productionOrderMapper.updateErpProductionOrder(erpProductionOrder);
        if (rows > 0 && "2".equals(erpProductionOrder.getStatus()) && (old == null || !"2".equals(old.getStatus())))
        {
            ErpProductionOrder fresh = productionOrderMapper.selectErpProductionOrderById(erpProductionOrder.getOrderId());
            processBomAndStock(fresh);
        }
        return rows;
    }

    /**
     * 按 BOM 领料（减库存）并完工入库（加库存），通过库存服务调整
     */
    private void processBomAndStock(ErpProductionOrder order)
    {
        ErpBom bomQuery = new ErpBom();
        bomQuery.setProductId(order.getProductId());
        bomQuery.setStatus("0");
        List<ErpBom> boms = bomMapper.selectErpBomList(bomQuery);
        if (boms != null && !boms.isEmpty())
        {
            ErpBom bom = boms.get(0);
            List<ErpBomItem> items = bomItemMapper.selectErpBomItemByBomId(bom.getBomId());
            BigDecimal factor = order.getPlanQty() == null ? BigDecimal.ONE : order.getPlanQty();
            for (ErpBomItem item : items)
            {
                adjustStock(PRODUCTION_WAREHOUSE, item.getMaterialId(), item.getQuantity().multiply(factor).negate());
            }
        }
        BigDecimal finishQty = (order.getFinishQty() != null && order.getFinishQty().signum() > 0) ? order.getFinishQty() : order.getPlanQty();
        if (finishQty != null)
        {
            adjustStock(FINISHED_WAREHOUSE, order.getProductId(), finishQty);
        }
    }

    /**
     * 调整库存（strict=false：不足截断为0，避免演示中断）
     */
    private void adjustStock(Long warehouseId, Long materialId, BigDecimal quantity)
    {
        StockAdjustReq req = new StockAdjustReq();
        req.setWarehouseId(warehouseId);
        req.setMaterialId(materialId);
        req.setQuantity(quantity);
        req.setStrict(Boolean.FALSE);
        AjaxResult result = stockFeignClient.adjust(req);
        if (result == null || !result.isSuccess())
        {
            throw new RuntimeException("库存联动失败");
        }
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