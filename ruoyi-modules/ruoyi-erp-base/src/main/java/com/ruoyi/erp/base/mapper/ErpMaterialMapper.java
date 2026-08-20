package com.ruoyi.erp.base.mapper;

import java.util.List;
import com.ruoyi.erp.base.domain.ErpMaterial;

/**
 * 物料档案Mapper接口
 * 
 * @author erp
 */
public interface ErpMaterialMapper
{
    /**
     * 查询物料档案
     */
    public ErpMaterial selectErpMaterialById(Long materialId);

    /**
     * 查询物料档案列表
     */
    public List<ErpMaterial> selectErpMaterialList(ErpMaterial erpMaterial);

    /**
     * 新增物料档案
     */
    public int insertErpMaterial(ErpMaterial erpMaterial);

    /**
     * 修改物料档案
     */
    public int updateErpMaterial(ErpMaterial erpMaterial);

    /**
     * 删除物料档案
     */
    public int deleteErpMaterialById(Long materialId);
}
