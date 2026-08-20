package com.ruoyi.erp.sale.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ruoyi.common.core.web.domain.AjaxResult;

/**
 * 库存服务 Feign 客户端
 * 
 * @author erp
 */
@FeignClient(contextId = "stockFeignClient", name = "ruoyi-erp-stock")
public interface StockFeignClient
{
    /**
     * 调整库存数量
     */
    @PostMapping("/stock/adjust")
    public AjaxResult adjust(@RequestBody StockAdjustReq req);
}
