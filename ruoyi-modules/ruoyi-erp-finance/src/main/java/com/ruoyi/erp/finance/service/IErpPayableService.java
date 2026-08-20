package com.ruoyi.erp.finance.service;

import java.util.List;
import com.ruoyi.erp.finance.domain.ErpPayable;

/**
 * 应付账款Service接口
 * 
 * @author erp
 */
public interface IErpPayableService
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

    /**
     * 生成应付账款（供其它服务 Feign 调用，按 billNo 幂等）
     */
    public int generateErpPayable(ErpPayable erpPayable);
}