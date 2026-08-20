package com.ruoyi.erp.base.mapper;

import java.util.List;
import com.ruoyi.erp.base.domain.ErpCustomer;

/**
 * 客户档案Mapper接口
 * 
 * @author erp
 */
public interface ErpCustomerMapper
{
    /**
     * 查询客户档案
     */
    public ErpCustomer selectErpCustomerById(Long customerId);

    /**
     * 查询客户档案列表
     */
    public List<ErpCustomer> selectErpCustomerList(ErpCustomer erpCustomer);

    /**
     * 新增客户档案
     */
    public int insertErpCustomer(ErpCustomer erpCustomer);

    /**
     * 修改客户档案
     */
    public int updateErpCustomer(ErpCustomer erpCustomer);

    /**
     * 删除客户档案
     */
    public int deleteErpCustomerById(Long customerId);
}
