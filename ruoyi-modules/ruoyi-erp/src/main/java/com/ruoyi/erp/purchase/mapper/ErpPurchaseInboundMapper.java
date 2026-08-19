package com.ruoyi.erp.purchase.mapper;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseInbound;

/**
 * 采购入库单Mapper接口
 * 
 * @author erp
 */
public interface ErpPurchaseInboundMapper
{
    /**
     * 查询采购入库单
     */
    public ErpPurchaseInbound selectErpPurchaseInboundById(Long inboundId);

    /**
     * 查询采购入库单列表
     */
    public List<ErpPurchaseInbound> selectErpPurchaseInboundList(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 新增采购入库单
     */
    public int insertErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 修改采购入库单
     */
    public int updateErpPurchaseInbound(ErpPurchaseInbound erpPurchaseInbound);

    /**
     * 删除采购入库单
     */
    public int deleteErpPurchaseInboundById(Long inboundId);
}