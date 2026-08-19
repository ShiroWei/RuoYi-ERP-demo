package com.ruoyi.erp.sale.service;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleReturn;

/**
 * 销售退货单Service接口
 * 
 * @author erp
 */
public interface IErpSaleReturnService
{
    /**
     * 查询销售退货单
     */
    public ErpSaleReturn selectErpSaleReturnById(Long returnId);

    /**
     * 查询销售退货单列表
     */
    public List<ErpSaleReturn> selectErpSaleReturnList(ErpSaleReturn ErpSaleReturn);

    /**
     * 新增销售退货单
     */
    public int insertErpSaleReturn(ErpSaleReturn ErpSaleReturn);

    /**
     * 修改销售退货单
     */
    public int updateErpSaleReturn(ErpSaleReturn ErpSaleReturn);

    /**
     * 删除销售退货单
     */
    public int deleteErpSaleReturnById(Long returnId);

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpSaleReturn(Long returnId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpSaleReturn(Long returnId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpSaleReturn(Long returnId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpSaleReturn(Long returnId);
}