package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Purchase微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiPurchaseApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiPurchaseApplication.class, args);
        System.out.println("( ( ( (  Purchase module started  ) ) ) )");
    }
}
