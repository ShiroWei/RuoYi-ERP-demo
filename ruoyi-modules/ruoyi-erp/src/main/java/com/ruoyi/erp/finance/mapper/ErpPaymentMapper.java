package com.ruoyi.erp.finance.mapper;

import java.util.List;
import com.ruoyi.erp.finance.domain.ErpPayment;

/**
 * 收付款单Mapper接口
 * 
 * @author erp
 */
public interface ErpPaymentMapper
{
    /**
     * 查询收付款单
     */
    public ErpPayment selectErpPaymentById(Long paymentId);

    /**
     * 查询收付款单列表
     */
    public List<ErpPayment> selectErpPaymentList(ErpPayment erpPayment);

    /**
     * 新增收付款单
     */
    public int insertErpPayment(ErpPayment erpPayment);

    /**
     * 修改收付款单
     */
    public int updateErpPayment(ErpPayment erpPayment);

    /**
     * 删除收付款单
     */
    public int deleteErpPaymentById(Long paymentId);
}