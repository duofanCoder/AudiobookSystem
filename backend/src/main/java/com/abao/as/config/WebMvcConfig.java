package com.abao.as.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("http://127.0.0.1:3000")
                .allowCredentials(true)
                .maxAge(3600);
    }
    @Value("${user.file.rootPath}")
    private String rootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+rootPath);
        //Mac或Linux下(没有CDEF盘符)
    }
}
