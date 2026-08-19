package com.ruoyi.erp.production.mapper;

import java.util.List;
import com.ruoyi.erp.production.domain.ErpBomItem;

/**
 * 物料清单(BOM)明细Mapper接口
 * 
 * @author erp
 */
public interface ErpBomItemMapper
{
    /**
     * 查询物料清单(BOM)明细
     */
    public ErpBomItem selectErpBomItemById(Long itemId);

    /**
     * 查询物料清单(BOM)明细列表
     */
    public List<ErpBomItem> selectErpBomItemList(ErpBomItem erpBomItem);

    /**
     * 根据BOM ID查询明细列表
     */
    public List<ErpBomItem> selectErpBomItemByBomId(Long bomId);

    /**
     * 新增物料清单(BOM)明细
     */
    public int insertErpBomItem(ErpBomItem erpBomItem);

    /**
     * 修改物料清单(BOM)明细
     */
    public int updateErpBomItem(ErpBomItem erpBomItem);

    /**
     * 删除物料清单(BOM)明细
     */
    public int deleteErpBomItemById(Long itemId);

    /**
     * 根据BOM ID删除明细
     */
    public int deleteErpBomItemByBomId(Long bomId);
}