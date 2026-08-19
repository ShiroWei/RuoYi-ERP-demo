package com.ruoyi.erp.base.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.base.domain.ErpSupplier;
import com.ruoyi.erp.base.mapper.ErpSupplierMapper;
import com.ruoyi.erp.base.service.IErpSupplierService;

/**
 * 供应商档案Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpSupplierServiceImpl implements IErpSupplierService
{
    @Autowired
    private ErpSupplierMapper erpSupplierMapper;

    /**
     * 查询供应商档案
     */
    @Override
    public ErpSupplier selectErpSupplierById(Long supplierId)
    {
        return erpSupplierMapper.selectErpSupplierById(supplierId);
    }

    /**
     * 查询供应商档案列表
     */
    @Override
    public List<ErpSupplier> selectErpSupplierList(ErpSupplier erpSupplier)
    {
        return erpSupplierMapper.selectErpSupplierList(erpSupplier);
    }

    /**
     * 新增供应商档案
     */
    @Override
    public int insertErpSupplier(ErpSupplier erpSupplier)
    {
        erpSupplier.setCreateBy(SecurityUtils.getUsername());
        erpSupplier.setCreateTime(DateUtils.getNowDate());
        return erpSupplierMapper.insertErpSupplier(erpSupplier);
    }

    /**
     * 修改供应商档案
     */
    @Override
    public int updateErpSupplier(ErpSupplier erpSupplier)
    {
        erpSupplier.setUpdateBy(SecurityUtils.getUsername());
        erpSupplier.setUpdateTime(DateUtils.getNowDate());
        return erpSupplierMapper.updateErpSupplier(erpSupplier);
    }

    /**
     * 删除供应商档案
     */
    @Override
    public int deleteErpSupplierById(Long supplierId)
    {
        return erpSupplierMapper.deleteErpSupplierById(supplierId);
    }
}