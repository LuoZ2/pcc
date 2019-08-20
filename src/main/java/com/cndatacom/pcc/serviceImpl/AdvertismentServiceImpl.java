package com.cndatacom.pcc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.dao.AdvertismentMapper;
import com.cndatacom.pcc.domain.Advertisment;
import com.cndatacom.pcc.service.AdvertismentService;

@Service
public class AdvertismentServiceImpl implements AdvertismentService {

	@Autowired
	private AdvertismentMapper advertismentMapper;

	@Override
	public List<String> getAdvertisment() {
		QueryWrapper<Advertisment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("adver_status", 1);
		queryWrapper.orderByDesc("created");
		queryWrapper.last("limit 5");
		List<Advertisment> list = advertismentMapper.selectList(queryWrapper);
		List<String> strs = new ArrayList<String>();
		for (Advertisment advertisment : list) {
			strs.add(advertisment.getAdverimage());
		}
		return strs;
	}

	@Override
	public List<Advertisment> getAds() {

		return advertismentMapper.selectList(null);
	}

	@Override
	public boolean updateAds(Advertisment ads) {
		int isSuccess = advertismentMapper.updateById(ads);
		if (isSuccess > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Advertisment getAdsByImageUrl(String imageUrl) {
		QueryWrapper<Advertisment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("adver_image", imageUrl);
		Advertisment ads = advertismentMapper.selectOne(queryWrapper);
		return ads;
	}

}
