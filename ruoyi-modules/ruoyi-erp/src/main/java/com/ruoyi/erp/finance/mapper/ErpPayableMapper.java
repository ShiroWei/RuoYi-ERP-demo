package com.ruoyi.erp.finance.mapper;

import java.util.List;
import com.ruoyi.erp.finance.domain.ErpPayable;

/**
 * 应付账款Mapper接口
 * 
 * @author erp
 */
public interface ErpPayableMapper
{
    /**
     * 查询应付账款
     */
    public ErpPayable selectErpPayableById(Long payableId);

    /**
     * 查询应付账款列表
     */
    public List<ErpPayable> selectErpPayableList(ErpPayable erpPayable);

    /**
     * 新增应付账款
     */
    public int insertErpPayable(ErpPayable erpPayable);

    /**
     * 修改应付账款
     */
    public int updateErpPayable(ErpPayable erpPayable);

    /**
     * 删除应付账款
     */
    public int deleteErpPayableById(Long payableId);
}