package com.ruoyi.erp.purchase.mapper;

import java.util.List;
import com.ruoyi.erp.purchase.domain.ErpPurchaseReturn;

/**
 * 采购退货单Mapper接口
 * 
 * @author erp
 */
public interface ErpPurchaseReturnMapper
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
}