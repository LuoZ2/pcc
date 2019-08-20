package com.cndatacom.pcc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cndatacom.pcc.filter.MyInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	  * 初始化
	  * 以bean的形式注册MyInterceptor拦截器到Spring中
	  * @return MyInterceptor
	  */
	 @Bean
	 public MyInterceptor myInterceptor() {
	  return new MyInterceptor();
	 }
	 
	 /**
	  * 添加拦截器,注册拦截器
	  */
	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
	  // TODO Auto-generated method stub
	  registry.addInterceptor(myInterceptor()).addPathPatterns("/user/**")
	            .addPathPatterns("/bgm/**")
	     .addPathPatterns("/video/upload", "/video/uploadCover",
	         "/video/userLike", "/video/userUnLike",
	         "/video/saveComment")
	                .excludePathPatterns("/user/queryPublisher");
	  
	 }
}
