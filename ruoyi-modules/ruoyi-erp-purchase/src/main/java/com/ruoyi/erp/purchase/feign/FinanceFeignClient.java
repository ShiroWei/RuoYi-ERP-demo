package com.ruoyi.erp.purchase.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ruoyi.common.core.web.domain.AjaxResult;

/**
 * 财务服务 Feign 客户端
 * 
 * @author erp
 */
@FeignClient(contextId = "financeFeignClient", name = "ruoyi-erp-finance")
public interface FinanceFeignClient
{
    /**
     * 生成应付账款
     */
    @PostMapping("/finance/payable/internal/payable")
    public AjaxResult createPayable(@RequestBody PayableReq req);
}
