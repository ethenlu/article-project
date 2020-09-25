package com.bytoday.common.config;

import com.bytoday.common.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JWTInterceptor())
			    .addPathPatterns("/**")			//所有接口都拦截
				.excludePathPatterns("/user/login") //所有登录都可通过
				.excludePathPatterns("/user/register");//所有注册都可通过
//				.excludePathPatterns("/swagger-ui.html#");//swagger都可通过
	}
}
