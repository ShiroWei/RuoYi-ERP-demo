package com.ruoyi.erp.stock.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.erp.stock.domain.ErpStock;

/**
 * 库存Mapper接口
 * 
 * @author erp
 */
public interface ErpStockMapper
{
    /**
     * 查询库存
     */
    public ErpStock selectErpStockById(Long stockId);

    /**
     * 查询库存列表
     */
    public List<ErpStock> selectErpStockList(ErpStock erpStock);

    /**
     * 新增库存
     */
    public int insertErpStock(ErpStock erpStock);

    /**
     * 修改库存
     */
    public int updateErpStock(ErpStock erpStock);

    /**
     * 删除库存
     */
    public int deleteErpStockById(Long stockId);

    /**
     * 按仓库+物料查询库存
     */
    public ErpStock selectErpStockByWarehouseAndMaterial(@Param("warehouseId") Long warehouseId, @Param("materialId") Long materialId);

    /**
     * 调整库存数量（存在则累加，不存在则新增，quantity 可为负数）
     */
    public int insertOrAddStock(@Param("warehouseId") Long warehouseId, @Param("materialId") Long materialId, @Param("quantity") BigDecimal quantity);
}