package com.gdschanyang.homepage.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("api.html")
                .addResourceLocations("classpath:/static/docs/")
                .setCachePeriod(20);
    }
}
