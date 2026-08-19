package com.ruoyi.erp.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.stock.domain.ErpStockRecord;
import com.ruoyi.erp.stock.mapper.ErpStockRecordMapper;
import com.ruoyi.erp.stock.service.IErpStockRecordService;

/**
 * 出入库记录Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpStockRecordServiceImpl implements IErpStockRecordService
{
    @Autowired
    private ErpStockRecordMapper stockRecordMapper;

    /**
     * 查询出入库记录
     */
    @Override
    public ErpStockRecord selectErpStockRecordById(Long recordId)
    {
        return stockRecordMapper.selectErpStockRecordById(recordId);
    }

    /**
     * 查询出入库记录列表
     */
    @Override
    public List<ErpStockRecord> selectErpStockRecordList(ErpStockRecord erpStockRecord)
    {
        return stockRecordMapper.selectErpStockRecordList(erpStockRecord);
    }

    /**
     * 新增出入库记录
     */
    @Override
    public int insertErpStockRecord(ErpStockRecord erpStockRecord)
    {
        erpStockRecord.setRecordNo(generateRecordNo());
        erpStockRecord.setCreateBy(SecurityUtils.getUsername());
        erpStockRecord.setCreateTime(DateUtils.getNowDate());
        return stockRecordMapper.insertErpStockRecord(erpStockRecord);
    }

    /**
     * 修改出入库记录
     */
    @Override
    public int updateErpStockRecord(ErpStockRecord erpStockRecord)
    {
        erpStockRecord.setUpdateBy(SecurityUtils.getUsername());
        erpStockRecord.setUpdateTime(DateUtils.getNowDate());
        return stockRecordMapper.updateErpStockRecord(erpStockRecord);
    }

    /**
     * 删除出入库记录
     */
    @Override
    public int deleteErpStockRecordById(Long recordId)
    {
        return stockRecordMapper.deleteErpStockRecordById(recordId);
    }

    /**
     * 生成记录单号：SR + yyyyMMddHHmmss
     */
    private String generateRecordNo()
    {
        return "SR" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}