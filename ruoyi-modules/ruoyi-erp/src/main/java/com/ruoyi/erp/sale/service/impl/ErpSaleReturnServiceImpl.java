package com.ruoyi.erp.sale.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.sale.domain.ErpSaleReturn;
import com.ruoyi.erp.sale.mapper.ErpSaleReturnMapper;
import com.ruoyi.erp.sale.service.IErpSaleReturnService;

/**
 * 销售退货单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpSaleReturnServiceImpl implements IErpSaleReturnService
{
    @Autowired
    private ErpSaleReturnMapper saleReturnMapper;

    /**
     * 查询销售退货单
     */
    @Override
    public ErpSaleReturn selectErpSaleReturnById(Long returnId)
    {
        return saleReturnMapper.selectErpSaleReturnById(returnId);
    }

    /**
     * 查询销售退货单列表
     */
    @Override
    public List<ErpSaleReturn> selectErpSaleReturnList(ErpSaleReturn ErpSaleReturn)
    {
        return saleReturnMapper.selectErpSaleReturnList(ErpSaleReturn);
    }

    /**
     * 新增销售退货单
     */
    @Override
    public int insertErpSaleReturn(ErpSaleReturn ErpSaleReturn)
    {
        ErpSaleReturn.setReturnNo(generateReturnNo());
        ErpSaleReturn.setStatus("0");
        ErpSaleReturn.setCreateBy(SecurityUtils.getUsername());
        ErpSaleReturn.setCreateTime(DateUtils.getNowDate());
        return saleReturnMapper.insertErpSaleReturn(ErpSaleReturn);
    }

    /**
     * 修改销售退货单
     */
    @Override
    public int updateErpSaleReturn(ErpSaleReturn ErpSaleReturn)
    {
        ErpSaleReturn.setUpdateBy(SecurityUtils.getUsername());
        ErpSaleReturn.setUpdateTime(DateUtils.getNowDate());
        return saleReturnMapper.updateErpSaleReturn(ErpSaleReturn);
    }

    /**
     * 删除销售退货单
     */
    @Override
    public int deleteErpSaleReturnById(Long returnId)
    {
        return saleReturnMapper.deleteErpSaleReturnById(returnId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpSaleReturn(Long returnId)
    {
        return updateStatus(returnId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long returnId, String status)
    {
        ErpSaleReturn ret = new ErpSaleReturn();
        ret.setReturnId(returnId);
        ret.setStatus(status);
        ret.setUpdateBy(SecurityUtils.getUsername());
        ret.setUpdateTime(DateUtils.getNowDate());
        return saleReturnMapper.updateErpSaleReturn(ret);
    }

    /**
     * 生成退货单号：RTPO + yyyyMMddHHmmss
     */
    private String generateReturnNo()
    {
        return "RTSO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}