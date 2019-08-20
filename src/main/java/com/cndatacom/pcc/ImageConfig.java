package com.cndatacom.pcc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * 
 * @author luo
 * 图片映射路径
 *
 */
@Configuration
public class ImageConfig extends WebMvcConfigurationSupport{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/uploadImages/**").addResourceLocations("file:D:/学习/xinghuo/images/complaintImages/")
		.addResourceLocations("file:D:/学习/xinghuo/images/licenseImages/")
		.addResourceLocations("file:D:/学习/xinghuo/images/stuImages/")
		.addResourceLocations("file:D:/学习/xinghuo/images/qrcode/")
		.addResourceLocations("file:D:/学习/xinghuo/images/ads/");
	}
}
