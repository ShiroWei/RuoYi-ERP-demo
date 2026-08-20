package com.ruoyi.erp.production.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.production.domain.ErpBom;
import com.ruoyi.erp.production.domain.ErpBomItem;
import com.ruoyi.erp.production.mapper.ErpBomItemMapper;
import com.ruoyi.erp.production.mapper.ErpBomMapper;
import com.ruoyi.erp.production.service.IErpBomService;

/**
 * 物料清单(BOM)Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpBomServiceImpl implements IErpBomService
{
    @Autowired
    private ErpBomMapper bomMapper;

    @Autowired
    private ErpBomItemMapper bomItemMapper;

    /**
     * 查询物料清单(BOM)
     */
    @Override
    public ErpBom selectErpBomById(Long bomId)
    {
        ErpBom bom = bomMapper.selectErpBomById(bomId);
        if (bom != null)
        {
            bom.setItems(bomItemMapper.selectErpBomItemByBomId(bomId));
        }
        return bom;
    }

    /**
     * 查询物料清单(BOM)列表
     */
    @Override
    public List<ErpBom> selectErpBomList(ErpBom erpBom)
    {
        return bomMapper.selectErpBomList(erpBom);
    }

    /**
     * 新增物料清单(BOM)（主表 + 明细行）
     */
    @Override
    @Transactional
    public int insertErpBom(ErpBom erpBom)
    {
        erpBom.setBomNo(generateBomNo());
        erpBom.setCreateBy(SecurityUtils.getUsername());
        erpBom.setCreateTime(DateUtils.getNowDate());
        int result = bomMapper.insertErpBom(erpBom);
        if (erpBom.getItems() != null)
        {
            for (ErpBomItem item : erpBom.getItems())
            {
                item.setBomId(erpBom.getBomId());
                bomItemMapper.insertErpBomItem(item);
            }
        }
        return result;
    }

    /**
     * 修改物料清单(BOM)（重建明细行）
     */
    @Override
    @Transactional
    public int updateErpBom(ErpBom erpBom)
    {
        erpBom.setUpdateBy(SecurityUtils.getUsername());
        erpBom.setUpdateTime(DateUtils.getNowDate());
        int result = bomMapper.updateErpBom(erpBom);
        bomItemMapper.deleteErpBomItemByBomId(erpBom.getBomId());
        if (erpBom.getItems() != null)
        {
            for (ErpBomItem item : erpBom.getItems())
            {
                item.setBomId(erpBom.getBomId());
                bomItemMapper.insertErpBomItem(item);
            }
        }
        return result;
    }

    /**
     * 删除物料清单(BOM)
     */
    @Override
    @Transactional
    public int deleteErpBomById(Long bomId)
    {
        bomItemMapper.deleteErpBomItemByBomId(bomId);
        return bomMapper.deleteErpBomById(bomId);
    }

    /**
     * 生成BOM编号：BOM + yyyyMMddHHmmss
     */
    private String generateBomNo()
    {
        return "BOM" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}