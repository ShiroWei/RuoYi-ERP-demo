package com.ruoyi.erp.sale.mapper;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleOutbound;

/**
 * 销售出库单Mapper接口
 * 
 * @author erp
 */
public interface ErpSaleOutboundMapper
{
    /**
     * 查询销售出库单
     */
    public ErpSaleOutbound selectErpSaleOutboundById(Long outboundId);

    /**
     * 查询销售出库单列表
     */
    public List<ErpSaleOutbound> selectErpSaleOutboundList(ErpSaleOutbound ErpSaleOutbound);

    /**
     * 新增销售出库单
     */
    public int insertErpSaleOutbound(ErpSaleOutbound ErpSaleOutbound);

    /**
     * 修改销售出库单
     */
    public int updateErpSaleOutbound(ErpSaleOutbound ErpSaleOutbound);

    /**
     * 删除销售出库单
     */
    public int deleteErpSaleOutboundById(Long outboundId);
}