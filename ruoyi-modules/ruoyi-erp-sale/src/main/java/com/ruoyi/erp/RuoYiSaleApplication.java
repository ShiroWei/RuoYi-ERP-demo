package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Sale微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiSaleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiSaleApplication.class, args);
        System.out.println("( ( ( (  Sale module started  ) ) ) )");
    }
}
