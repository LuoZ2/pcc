package com.cndatacom.pcc.service;


import java.util.List;

import com.cndatacom.pcc.domain.Advertisment;

public interface AdvertismentService {

	//获取最新五条广告
	public List<String> getAdvertisment();

	public List<Advertisment> getAds();

	public boolean updateAds(Advertisment ads);

	public Advertisment getAdsByImageUrl(String imageUrl);
}
