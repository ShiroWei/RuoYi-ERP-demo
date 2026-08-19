package com.ruoyi.erp.base.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.base.domain.ErpMaterial;
import com.ruoyi.erp.base.mapper.ErpMaterialMapper;
import com.ruoyi.erp.base.service.IErpMaterialService;

/**
 * 物料档案Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpMaterialServiceImpl implements IErpMaterialService
{
    @Autowired
    private ErpMaterialMapper erpMaterialMapper;

    /**
     * 查询物料档案
     */
    @Override
    public ErpMaterial selectErpMaterialById(Long materialId)
    {
        return erpMaterialMapper.selectErpMaterialById(materialId);
    }

    /**
     * 查询物料档案列表
     */
    @Override
    public List<ErpMaterial> selectErpMaterialList(ErpMaterial erpMaterial)
    {
        return erpMaterialMapper.selectErpMaterialList(erpMaterial);
    }

    /**
     * 新增物料档案
     */
    @Override
    public int insertErpMaterial(ErpMaterial erpMaterial)
    {
        erpMaterial.setCreateBy(SecurityUtils.getUsername());
        erpMaterial.setCreateTime(DateUtils.getNowDate());
        return erpMaterialMapper.insertErpMaterial(erpMaterial);
    }

    /**
     * 修改物料档案
     */
    @Override
    public int updateErpMaterial(ErpMaterial erpMaterial)
    {
        erpMaterial.setUpdateBy(SecurityUtils.getUsername());
        erpMaterial.setUpdateTime(DateUtils.getNowDate());
        return erpMaterialMapper.updateErpMaterial(erpMaterial);
    }

    /**
     * 删除物料档案
     */
    @Override
    public int deleteErpMaterialById(Long materialId)
    {
        return erpMaterialMapper.deleteErpMaterialById(materialId);
    }
}