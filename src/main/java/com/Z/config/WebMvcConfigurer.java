package com.Z.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.Z.controller.interceptor.HomeInterceptor;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HomeInterceptor()).addPathPatterns("/home/**");
		super.addInterceptors(registry);
	}
	
	/**
	 * 开放系统资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:META-INF/resources/")
				.addResourceLocations("file:C:/ES/");
	}
	
}
