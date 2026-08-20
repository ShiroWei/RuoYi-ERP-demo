package com.ruoyi.erp.base.mapper;

import java.util.List;
import com.ruoyi.erp.base.domain.ErpSupplier;

/**
 * 供应商档案Mapper接口
 * 
 * @author erp
 */
public interface ErpSupplierMapper
{
    /**
     * 查询供应商档案
     */
    public ErpSupplier selectErpSupplierById(Long supplierId);

    /**
     * 查询供应商档案列表
     */
    public List<ErpSupplier> selectErpSupplierList(ErpSupplier erpSupplier);

    /**
     * 新增供应商档案
     */
    public int insertErpSupplier(ErpSupplier erpSupplier);

    /**
     * 修改供应商档案
     */
    public int updateErpSupplier(ErpSupplier erpSupplier);

    /**
     * 删除供应商档案
     */
    public int deleteErpSupplierById(Long supplierId);
}
