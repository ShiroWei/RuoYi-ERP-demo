package com.ruoyi.erp.base.service;

import java.util.List;
import com.ruoyi.erp.base.domain.ErpSupplier;

/**
 * 供应商档案Service接口
 * 
 * @author erp
 */
public interface IErpSupplierService
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