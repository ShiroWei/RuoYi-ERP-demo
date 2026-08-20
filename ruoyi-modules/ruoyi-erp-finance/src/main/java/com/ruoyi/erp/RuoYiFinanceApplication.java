package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Finance微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiFinanceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiFinanceApplication.class, args);
        System.out.println("( ( ( (  Finance module started  ) ) ) )");
    }
}
