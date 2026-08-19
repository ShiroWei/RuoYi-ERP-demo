package com.ruoyi.erp.finance.service;

import java.util.List;
import com.ruoyi.erp.finance.domain.ErpReceivable;

/**
 * 应收账款Service接口
 * 
 * @author erp
 */
public interface IErpReceivableService
{
    /**
     * 查询应收账款
     */
    public ErpReceivable selectErpReceivableById(Long receivableId);

    /**
     * 查询应收账款列表
     */
    public List<ErpReceivable> selectErpReceivableList(ErpReceivable erpReceivable);

    /**
     * 新增应收账款
     */
    public int insertErpReceivable(ErpReceivable erpReceivable);

    /**
     * 修改应收账款
     */
    public int updateErpReceivable(ErpReceivable erpReceivable);

    /**
     * 删除应收账款
     */
    public int deleteErpReceivableById(Long receivableId);
}