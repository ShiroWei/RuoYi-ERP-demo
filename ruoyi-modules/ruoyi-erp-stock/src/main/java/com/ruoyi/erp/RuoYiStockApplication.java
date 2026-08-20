package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Stock微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiStockApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiStockApplication.class, args);
        System.out.println("( ( ( (  Stock module started  ) ) ) )");
    }
}
