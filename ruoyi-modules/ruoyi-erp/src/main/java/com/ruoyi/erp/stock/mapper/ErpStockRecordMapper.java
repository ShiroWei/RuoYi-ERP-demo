package com.ruoyi.erp.stock.mapper;

import java.util.List;
import com.ruoyi.erp.stock.domain.ErpStockRecord;

/**
 * 出入库记录Mapper接口
 * 
 * @author erp
 */
public interface ErpStockRecordMapper
{
    /**
     * 查询出入库记录
     */
    public ErpStockRecord selectErpStockRecordById(Long recordId);

    /**
     * 查询出入库记录列表
     */
    public List<ErpStockRecord> selectErpStockRecordList(ErpStockRecord erpStockRecord);

    /**
     * 新增出入库记录
     */
    public int insertErpStockRecord(ErpStockRecord erpStockRecord);

    /**
     * 修改出入库记录
     */
    public int updateErpStockRecord(ErpStockRecord erpStockRecord);

    /**
     * 删除出入库记录
     */
    public int deleteErpStockRecordById(Long recordId);
}