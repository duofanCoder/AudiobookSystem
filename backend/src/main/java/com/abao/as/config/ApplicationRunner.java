package com.abao.as.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

@Slf4j
@Component
public class ApplicationRunner extends ContextLoaderListener implements org.springframework.boot.ApplicationRunner {

    @Value("${server.port}")
    private int port;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("项目部署完成，swagger访问地址：http://localhost:" + port+"/swagger-ui/index.html");
        System.out.println("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdW9mYW4iLCJyb2xlcyI6WyJBRE1JTiJdLCJleHAiOjE2NTEyODY2Mjd9.80FXFerK76H463mscQkp5WWUFbJ87nAkIDEy8ZESk2p80dxOtm__VNcvhaiTPhBg3cRijAUUnksvpj3M8Rm9mA");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("关键配置信息：");
        log.info("Data Source：");
        log.info("  - url：{}", dataSourceProperties.getUrl());
        log.info("  - username：{}", dataSourceProperties.getUsername());
        log.info("  - password：{}", dataSourceProperties.getPassword());
    }
}
