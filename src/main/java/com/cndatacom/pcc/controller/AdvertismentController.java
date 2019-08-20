package com.cndatacom.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.domain.Advertisment;
import com.cndatacom.pcc.service.AdvertismentService;

@RestController
public class AdvertismentController {

	@Autowired
	private AdvertismentService advertismentService;
	//获取最新五条广告
	@RequestMapping(value = "/advertisment/getAdvertisment")
	public ResponseVO getAdvertisment() {
		
		List<String> list = advertismentService.getAdvertisment();
		if(list.size() > 0) {
			return ResponseVO.success(list);
		}else {
			return ResponseVO.serviceFail("业务异常");
		}	
	}
	//获取最新五条广告详情
		@RequestMapping(value = "/advertisment/getAdsdetail")
		public ResponseVO getAdsdetail(String imageUrl) {
			System.out.println(imageUrl);
		Advertisment adsDetail = advertismentService.getAdsByImageUrl(imageUrl);
			if(adsDetail != null) {
				return ResponseVO.success(adsDetail);
			}else {
				return ResponseVO.serviceFail("业务异常");
			}	
		}
}
