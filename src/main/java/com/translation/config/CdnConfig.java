package com.translation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CdnConfig implements WebMvcConfigurer {

    @Value("${app.cdn.enabled:false}")
    private boolean cdnEnabled;

    @Value("${app.cdn.url}")
    private String cdnUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (cdnEnabled) {
            registry.addResourceHandler("/static/**")
                   .addResourceLocations(cdnUrl + "/static/");
        } else {
            registry.addResourceHandler("/static/**")
                   .addResourceLocations("classpath:/static/");
        }
    }
}
