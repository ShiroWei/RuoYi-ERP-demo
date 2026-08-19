package com.ruoyi.erp.purchase.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.erp.purchase.domain.ErpPurchaseReturn;
import com.ruoyi.erp.purchase.mapper.ErpPurchaseReturnMapper;
import com.ruoyi.erp.purchase.service.IErpPurchaseReturnService;

/**
 * 采购退货单Service业务层处理
 * 
 * @author erp
 */
@Service
public class ErpPurchaseReturnServiceImpl implements IErpPurchaseReturnService
{
    @Autowired
    private ErpPurchaseReturnMapper purchaseReturnMapper;

    /**
     * 查询采购退货单
     */
    @Override
    public ErpPurchaseReturn selectErpPurchaseReturnById(Long returnId)
    {
        return purchaseReturnMapper.selectErpPurchaseReturnById(returnId);
    }

    /**
     * 查询采购退货单列表
     */
    @Override
    public List<ErpPurchaseReturn> selectErpPurchaseReturnList(ErpPurchaseReturn erpPurchaseReturn)
    {
        return purchaseReturnMapper.selectErpPurchaseReturnList(erpPurchaseReturn);
    }

    /**
     * 新增采购退货单
     */
    @Override
    public int insertErpPurchaseReturn(ErpPurchaseReturn erpPurchaseReturn)
    {
        erpPurchaseReturn.setReturnNo(generateReturnNo());
        erpPurchaseReturn.setStatus("0");
        erpPurchaseReturn.setCreateBy(SecurityUtils.getUsername());
        erpPurchaseReturn.setCreateTime(DateUtils.getNowDate());
        return purchaseReturnMapper.insertErpPurchaseReturn(erpPurchaseReturn);
    }

    /**
     * 修改采购退货单
     */
    @Override
    public int updateErpPurchaseReturn(ErpPurchaseReturn erpPurchaseReturn)
    {
        erpPurchaseReturn.setUpdateBy(SecurityUtils.getUsername());
        erpPurchaseReturn.setUpdateTime(DateUtils.getNowDate());
        return purchaseReturnMapper.updateErpPurchaseReturn(erpPurchaseReturn);
    }

    /**
     * 删除采购退货单
     */
    @Override
    public int deleteErpPurchaseReturnById(Long returnId)
    {
        return purchaseReturnMapper.deleteErpPurchaseReturnById(returnId);
    }

    /**
     * 提交审核（草稿 -> 待审核）
     */
    @Override
    public int submitErpPurchaseReturn(Long returnId)
    {
        return updateStatus(returnId, "1");
    }

    /**
     * 审核通过（待审核 -> 审核通过）
     */
    @Override
    public int approveErpPurchaseReturn(Long returnId)
    {
        return updateStatus(returnId, "2");
    }

    /**
     * 审核驳回（待审核 -> 已驳回）
     */
    @Override
    public int rejectErpPurchaseReturn(Long returnId)
    {
        return updateStatus(returnId, "3");
    }

    /**
     * 完成（审核通过 -> 已完成）
     */
    @Override
    public int completeErpPurchaseReturn(Long returnId)
    {
        return updateStatus(returnId, "4");
    }

    /**
     * 更新单据状态
     */
    private int updateStatus(Long returnId, String status)
    {
        ErpPurchaseReturn ret = new ErpPurchaseReturn();
        ret.setReturnId(returnId);
        ret.setStatus(status);
        ret.setUpdateBy(SecurityUtils.getUsername());
        ret.setUpdateTime(DateUtils.getNowDate());
        return purchaseReturnMapper.updateErpPurchaseReturn(ret);
    }

    /**
     * 生成退货单号：RTPO + yyyyMMddHHmmss
     */
    private String generateReturnNo()
    {
        return "RTPO" + DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate());
    }
}