package com.ruoyi.erp.production.service;

import java.util.List;
import com.ruoyi.erp.production.domain.ErpBom;

/**
 * 物料清单(BOM)Service接口
 * 
 * @author erp
 */
public interface IErpBomService
{
    /**
     * 查询物料清单(BOM)
     */
    public ErpBom selectErpBomById(Long bomId);

    /**
     * 查询物料清单(BOM)列表
     */
    public List<ErpBom> selectErpBomList(ErpBom erpBom);

    /**
     * 新增物料清单(BOM)
     */
    public int insertErpBom(ErpBom erpBom);

    /**
     * 修改物料清单(BOM)
     */
    public int updateErpBom(ErpBom erpBom);

    /**
     * 删除物料清单(BOM)
     */
    public int deleteErpBomById(Long bomId);
}