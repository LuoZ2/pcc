package com.cndatacom.pcc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cndatacom.pcc.dao.StucontactMapper;
import com.cndatacom.pcc.domain.Stucontact;
import com.cndatacom.pcc.service.StucontactService;
@Service
public class StucontactServiceImpl implements StucontactService{

	@Autowired
	private StucontactMapper stucontactMapper;
	@Override
	public boolean getStucontactBytofromId(String id1, String id2) {
		QueryWrapper<Stucontact> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("to_id", id1);
		queryWrapper1.eq("from_id", id2);
		queryWrapper1.eq("status", 1);
		Stucontact stucontact1 = stucontactMapper.selectOne(queryWrapper1);
		QueryWrapper<Stucontact> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("to_id", id2);
		queryWrapper2.eq("from_id", id1);	
		queryWrapper2.eq("status", 1);
		Stucontact stucontact2 = stucontactMapper.selectOne(queryWrapper2);	
		boolean isContect = false;
		if(id1.equals(id2)||stucontact1 != null||stucontact2 != null) {
			isContect = true;
		}
		return isContect;
	}
	@Override
	public List<String> findAllContact(String myId,Integer status) {
		// 用于存放学生id
		List<String> stuId_list=new ArrayList<String>();
		QueryWrapper<Stucontact> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("to_id", myId);
		queryWrapper1.eq("status", status);
		List<Stucontact> list1 = stucontactMapper.selectList(queryWrapper1);
		for (Stucontact stucontact : list1) {
			System.out.println(stucontact.getFromId());
			stuId_list.add(stucontact.getFromId());
		}
		
		QueryWrapper<Stucontact> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("from_id", myId);
		queryWrapper2.eq("status", status);
		List<Stucontact> list2 = stucontactMapper.selectList(queryWrapper2);
		for (Stucontact stucontact : list2) {
			System.out.println(stucontact.getToId());
			stuId_list.add(stucontact.getToId());
		}
		return stuId_list;
	}
	
	@Override
	public List<String> findRequestContact(String myId, Integer status) {
		List<String> stuId_list=new ArrayList<String>();
		QueryWrapper<Stucontact> queryWrapper2 = new QueryWrapper<>();
		//别人向我申请，所以from_id=myId
		queryWrapper2.eq("to_id", myId);
		queryWrapper2.eq("status", status);
		List<Stucontact> list2 = stucontactMapper.selectList(queryWrapper2);
		for (Stucontact stucontact : list2) {
			stuId_list.add(stucontact.getFromId());
		}
		return stuId_list;
	}
	
	@Override
	public boolean yesrequest(String myId, String fromId) {
		QueryWrapper<Stucontact> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("from_id", fromId);
		queryWrapper2.eq("to_id", myId);
		Stucontact contact = stucontactMapper.selectOne(queryWrapper2);
		contact.setStatus(1);
		int num=stucontactMapper.updateById(contact);
		if(num>0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean norequest(String myId, String fromId) {
		QueryWrapper<Stucontact> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("from_id", fromId);
		queryWrapper2.eq("to_id", myId);
		int num=stucontactMapper.delete(queryWrapper2);
		if(num>0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean myrequest(Stucontact contact) {
		QueryWrapper<Stucontact> wrapper=new QueryWrapper<Stucontact>();
		wrapper.eq("from_id", contact.getFromId());
		wrapper.eq("to_id",contact.getToId());
		Stucontact contact1 = stucontactMapper.selectOne(wrapper);
		
		QueryWrapper<Stucontact> wrapper1=new QueryWrapper<Stucontact>();
		wrapper1.eq("from_id", contact.getFromId());
		wrapper1.eq("to_id",contact.getToId());
		Stucontact contact2 = stucontactMapper.selectOne(wrapper1);
		if(contact1!=null||contact2!=null) {
			return false;
		}else {
			int num=stucontactMapper.insert(contact);
			if(num>0) {
				return true;
			}else {
				return false;
			}
		}
	}
	@Override
	public Integer delFriendById(String myId, String friendId) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("to_id", myId);
		queryWrapper.or();
		queryWrapper.eq("from_id", friendId);
		Integer isSucces = stucontactMapper.delete(queryWrapper);
		return isSucces;
	}
	
	
	
	

}
