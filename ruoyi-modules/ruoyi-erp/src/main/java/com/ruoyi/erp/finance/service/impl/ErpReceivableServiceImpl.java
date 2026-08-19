package com.ruoyi.erp.finance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.finance.domain.ErpReceivable;
import com.ruoyi.erp.finance.mapper.ErpReceivableMapper;
import com.ruoyi.erp.finance.service.IErpReceivableService;

/**
 * 应收账款Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpReceivableServiceImpl implements IErpReceivableService
{
    @Autowired
    private ErpReceivableMapper receivableMapper;

    /**
     * 查询应收账款
     */
    @Override
    public ErpReceivable selectErpReceivableById(Long receivableId)
    {
        return receivableMapper.selectErpReceivableById(receivableId);
    }

    /**
     * 查询应收账款列表
     */
    @Override
    public List<ErpReceivable> selectErpReceivableList(ErpReceivable erpReceivable)
    {
        return receivableMapper.selectErpReceivableList(erpReceivable);
    }

    /**
     * 新增应收账款
     */
    @Override
    public int insertErpReceivable(ErpReceivable erpReceivable)
    {
        erpReceivable.setCreateBy(SecurityUtils.getUsername());
        erpReceivable.setCreateTime(DateUtils.getNowDate());
        return receivableMapper.insertErpReceivable(erpReceivable);
    }

    /**
     * 修改应收账款
     */
    @Override
    public int updateErpReceivable(ErpReceivable erpReceivable)
    {
        erpReceivable.setUpdateBy(SecurityUtils.getUsername());
        erpReceivable.setUpdateTime(DateUtils.getNowDate());
        return receivableMapper.updateErpReceivable(erpReceivable);
    }

    /**
     * 删除应收账款
     */
    @Override
    public int deleteErpReceivableById(Long receivableId)
    {
        return receivableMapper.deleteErpReceivableById(receivableId);
    }
}