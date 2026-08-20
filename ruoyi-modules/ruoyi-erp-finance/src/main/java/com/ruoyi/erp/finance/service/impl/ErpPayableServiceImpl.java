package com.ruoyi.erp.finance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.finance.domain.ErpPayable;
import com.ruoyi.erp.finance.mapper.ErpPayableMapper;
import com.ruoyi.erp.finance.service.IErpPayableService;

/**
 * 应付账款Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPayableServiceImpl implements IErpPayableService
{
    @Autowired
    private ErpPayableMapper payableMapper;

    /**
     * 查询应付账款
     */
    @Override
    public ErpPayable selectErpPayableById(Long payableId)
    {
        return payableMapper.selectErpPayableById(payableId);
    }

    /**
     * 查询应付账款列表
     */
    @Override
    public List<ErpPayable> selectErpPayableList(ErpPayable erpPayable)
    {
        return payableMapper.selectErpPayableList(erpPayable);
    }

    /**
     * 新增应付账款
     */
    @Override
    public int insertErpPayable(ErpPayable erpPayable)
    {
        erpPayable.setCreateBy(SecurityUtils.getUsername());
        erpPayable.setCreateTime(DateUtils.getNowDate());
        return payableMapper.insertErpPayable(erpPayable);
    }

    /**
     * 修改应付账款
     */
    @Override
    public int updateErpPayable(ErpPayable erpPayable)
    {
        erpPayable.setUpdateBy(SecurityUtils.getUsername());
        erpPayable.setUpdateTime(DateUtils.getNowDate());
        return payableMapper.updateErpPayable(erpPayable);
    }

    /**
     * 删除应付账款
     */
    @Override
    public int deleteErpPayableById(Long payableId)
    {
        return payableMapper.deleteErpPayableById(payableId);
    }

    /**
     * 生成应付账款（按 billNo 幂等）
     */
    @Override
    public int generateErpPayable(ErpPayable erpPayable)
    {
        ErpPayable query = new ErpPayable();
        query.setBillNo(erpPayable.getBillNo());
        List<ErpPayable> exist = payableMapper.selectErpPayableList(query);
        if (exist != null && !exist.isEmpty())
        {
            return 1;
        }
        erpPayable.setPaidAmount(erpPayable.getPaidAmount() == null ? java.math.BigDecimal.ZERO : erpPayable.getPaidAmount());
        erpPayable.setBalance(erpPayable.getAmount());
        erpPayable.setStatus("0");
        erpPayable.setCreateBy(SecurityUtils.getUsername());
        erpPayable.setCreateTime(DateUtils.getNowDate());
        return payableMapper.insertErpPayable(erpPayable);
    }
}