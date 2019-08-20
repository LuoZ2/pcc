package com.cndatacom.pcc.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cndatacom.pcc.dao.TraveldetailMapper;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.service.TraveldetailService;
@Service
public class TraveldetailServiceImpl implements TraveldetailService {

	@Autowired
	 private TraveldetailMapper traveldetailMapper;
	
	@Override
	public boolean checkJoinTeam(Long travelid, String myId) {
		QueryWrapper<Traveldetail> wrapper=new QueryWrapper<Traveldetail>();
		wrapper.eq("travelid", travelid);
		wrapper.eq("stu_id", myId);
		Traveldetail td = traveldetailMapper.selectOne(wrapper);
		if(td!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int addTraveldetail(Traveldetail traveldetail) {
		QueryWrapper<Traveldetail> wrapper=new QueryWrapper<Traveldetail>();
		wrapper.eq("travelid", traveldetail.getTravelid());
		wrapper.eq("stu_id", traveldetail.getStuId());
		Traveldetail td = traveldetailMapper.selectOne(wrapper);
			int isSuccess = traveldetailMapper.insert(traveldetail);
			return isSuccess;
	}

	@Override
	public List<Traveldetail> getTraveldetails(Long travelid) {
		QueryWrapper<Traveldetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("travelid", travelid);
		return traveldetailMapper.selectList(queryWrapper );
	}

	@Override
	public boolean deleteTraveldetail(Traveldetail traveldetail) {
		int num=traveldetailMapper.deleteById(traveldetail.getItemid());
		if(num>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateTraveldetail(Traveldetail trade) {
		// TODO Auto-generated method stub
		int num=traveldetailMapper.updateById(trade);
		if(num>0) {
			return true;
		}
		return false;
	}

	@Override
	public Traveldetail getBytravelidAndStuid(long travelid, String stu_id) {
		QueryWrapper<Traveldetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("travelid", travelid);
		queryWrapper.eq("stu_id", stu_id);
		Traveldetail t = traveldetailMapper.selectOne(queryWrapper);
		return t;
	}


}
