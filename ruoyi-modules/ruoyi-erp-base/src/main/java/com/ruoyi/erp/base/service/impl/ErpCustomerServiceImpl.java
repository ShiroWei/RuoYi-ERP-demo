package com.ruoyi.erp.base.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.base.domain.ErpCustomer;
import com.ruoyi.erp.base.mapper.ErpCustomerMapper;
import com.ruoyi.erp.base.service.IErpCustomerService;

/**
 * 客户档案Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpCustomerServiceImpl implements IErpCustomerService
{
    @Autowired
    private ErpCustomerMapper erpCustomerMapper;

    /**
     * 查询客户档案
     */
    @Override
    public ErpCustomer selectErpCustomerById(Long customerId)
    {
        return erpCustomerMapper.selectErpCustomerById(customerId);
    }

    /**
     * 查询客户档案列表
     */
    @Override
    public List<ErpCustomer> selectErpCustomerList(ErpCustomer erpCustomer)
    {
        return erpCustomerMapper.selectErpCustomerList(erpCustomer);
    }

    /**
     * 新增客户档案
     */
    @Override
    public int insertErpCustomer(ErpCustomer erpCustomer)
    {
        erpCustomer.setCreateBy(SecurityUtils.getUsername());
        erpCustomer.setCreateTime(DateUtils.getNowDate());
        return erpCustomerMapper.insertErpCustomer(erpCustomer);
    }

    /**
     * 修改客户档案
     */
    @Override
    public int updateErpCustomer(ErpCustomer erpCustomer)
    {
        erpCustomer.setUpdateBy(SecurityUtils.getUsername());
        erpCustomer.setUpdateTime(DateUtils.getNowDate());
        return erpCustomerMapper.updateErpCustomer(erpCustomer);
    }

    /**
     * 删除客户档案
     */
    @Override
    public int deleteErpCustomerById(Long customerId)
    {
        return erpCustomerMapper.deleteErpCustomerById(customerId);
    }
}