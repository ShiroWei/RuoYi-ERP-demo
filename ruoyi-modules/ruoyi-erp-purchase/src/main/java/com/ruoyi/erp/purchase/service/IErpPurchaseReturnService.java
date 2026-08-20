package com.ruoyi.erp.purchase.service;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseReturn;

/**
 * 采购退货单Service接口
 * 
 * @author erp
 */
public interface IErpPurchaseReturnService
{
    /**
     * 查询采购退货单
     */
    public ErpPurchaseReturn selectErpPurchaseReturnById(Long returnId);

    /**
     * 查询采购退货单列表
     */
    public List<ErpPurchaseReturn> selectErpPurchaseReturnList(ErpPurchaseReturn erpPurchaseReturn);

    /**
     * 新增采购退货单
     */
    public int insertErpPurchaseReturn(ErpPurchaseReturn erpPurchaseReturn);

    /**
     * 修改采购退货单
     */
    public int updateErpPurchaseReturn(ErpPurchaseReturn erpPurchaseReturn);

    /**
     * 删除采购退货单
     */
    public int deleteErpPurchaseReturnById(Long returnId);

    /**
     * 提交审核（草稿 -> 待审核）
     */
    public int submitErpPurchaseReturn(Long returnId);

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    public int approveErpPurchaseReturn(Long returnId);

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    public int rejectErpPurchaseReturn(Long returnId);

    /**
     * 完成（审核通过 -> 已完成）
     */
    public int completeErpPurchaseReturn(Long returnId);
}