package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Production微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiProductionApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiProductionApplication.class, args);
        System.out.println("( ( ( (  Production module started  ) ) ) )");
    }
}
