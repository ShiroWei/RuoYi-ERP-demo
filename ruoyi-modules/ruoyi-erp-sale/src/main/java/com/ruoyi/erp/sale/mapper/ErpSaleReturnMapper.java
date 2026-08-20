package com.ruoyi.erp.sale.mapper;

import java.util.List;
import com.ruoyi.erp.sale.domain.ErpSaleReturn;

/**
 * 销售退货单Mapper接口
 * 
 * @author erp
 */
public interface ErpSaleReturnMapper
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
}