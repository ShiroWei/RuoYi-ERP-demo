package com.ruoyi.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * Report微服务
 * 
 * @author erp
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiReportApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiReportApplication.class, args);
        System.out.println("( ( ( (  Report module started  ) ) ) )");
    }
}
