package com.ruoyi.erp.sale.service;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleOutbound;

/**
 * 销售出库单Service接口
 * 
 * @author erp
 */
public interface IErpSaleOutboundService
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

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpSaleOutbound(Long outboundId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpSaleOutbound(Long outboundId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpSaleOutbound(Long outboundId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpSaleOutbound(Long outboundId);
}