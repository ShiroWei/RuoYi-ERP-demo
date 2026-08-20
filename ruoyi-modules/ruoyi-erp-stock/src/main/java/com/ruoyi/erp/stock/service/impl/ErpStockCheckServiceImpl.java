package com.ruoyi.erp.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.stock.domain.ErpStockCheck;
import com.ruoyi.erp.stock.mapper.ErpStockCheckMapper;
import com.ruoyi.erp.stock.service.IErpStockCheckService;

/**
 * 库存盘点Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpStockCheckServiceImpl implements IErpStockCheckService
{
    @Autowired
    private ErpStockCheckMapper stockCheckMapper;

    /**
     * 查询库存盘点
     */
    @Override
    public ErpStockCheck selectErpStockCheckById(Long checkId)
    {
        return stockCheckMapper.selectErpStockCheckById(checkId);
    }

    /**
     * 查询库存盘点列表
     */
    @Override
    public List<ErpStockCheck> selectErpStockCheckList(ErpStockCheck erpStockCheck)
    {
        return stockCheckMapper.selectErpStockCheckList(erpStockCheck);
    }

    /**
     * 新增库存盘点
     */
    @Override
    public int insertErpStockCheck(ErpStockCheck erpStockCheck)
    {
        erpStockCheck.setCheckNo(generateCheckNo());
        erpStockCheck.setCreateBy(SecurityUtils.getUsername());
        erpStockCheck.setCreateTime(DateUtils.getNowDate());
        return stockCheckMapper.insertErpStockCheck(erpStockCheck);
    }

    /**
     * 修改库存盘点
     */
    @Override
    public int updateErpStockCheck(ErpStockCheck erpStockCheck)
    {
        erpStockCheck.setUpdateBy(SecurityUtils.getUsername());
        erpStockCheck.setUpdateTime(DateUtils.getNowDate());
        return stockCheckMapper.updateErpStockCheck(erpStockCheck);
    }

    /**
     * 删除库存盘点
     */
    @Override
    public int deleteErpStockCheckById(Long checkId)
    {
        return stockCheckMapper.deleteErpStockCheckById(checkId);
    }

    /**
     * 生成盘点单号：CK + yyyyMMddHHmmss
     */
    private String generateCheckNo()
    {
        return "CK" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}