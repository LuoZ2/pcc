package com.cndatacom.pcc.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cndatacom.pcc.VO.TravelInfo;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.dao.OrdersMapper;
import com.cndatacom.pcc.dao.StudentMapper;
import com.cndatacom.pcc.dao.TraveldetailMapper;
import com.cndatacom.pcc.dao.TravelsMapper;
import com.cndatacom.pcc.domain.Student;
import com.cndatacom.pcc.domain.Traveldetail;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.OrderService;
import com.cndatacom.pcc.service.TravelsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class TravelsServiceImpl implements TravelsService {

	@Autowired
	private TravelsMapper travelsMapper;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private TraveldetailMapper traveldetailMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int addTravel(Travels travel) {
		travel.setStatus(2);
		travelsMapper.insert(travel);

		// 组装traveldetail
		Traveldetail traveldetail = new Traveldetail();
		traveldetail.setItemid(System.currentTimeMillis());
		traveldetail.setCreated(travel.getCreated());
		traveldetail.setDestination(travel.getDestination());
		traveldetail.setOriginAddr(travel.getOriginAddr());
		traveldetail.setStarttime(travel.getStarttime());
		traveldetail.setStatus(1);
		traveldetail.setStuId(travel.getInitId());
		traveldetail.setTelephone(travel.getTelephone());
		traveldetail.setTravelid(travel.getTravelid());
		int isSuccess = traveldetailMapper.insert(traveldetail);
		return isSuccess;

	}

	@Override
	public PagedResult getTravels(int nums, int nowPage, String originAddr, String destination, String stuSex,
			Integer actualNum) {
		PagedResult travelsVO = new PagedResult();

		// 行程的限制条件，status为“2”即组队状态
		QueryWrapper<Travels> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status", 2);
		queryWrapper.orderByDesc("created");
		// 条件筛选 起点、目的地、预计人数、当前人数
		if (originAddr != "" && originAddr != null) {
			String originAddrStr = "%" + originAddr + "%";
			queryWrapper.like("origin_addr", originAddrStr);
		}
		if (destination != "" && destination != null) {
			String destinationStr = "%" + destination + "%";
			queryWrapper.like("destination", destinationStr);
		}
		if (stuSex != null) {
			QueryWrapper<Student> qw = new QueryWrapper<>();
			queryWrapper.exists("select stu_id as init_id from student where stu_sex = ' " + stuSex + "'");
		}
		if (actualNum != null) {
			queryWrapper.eq("actual_num", actualNum);
		}

		Page<Travels> page = new Page<>(nowPage, nums);
		List<Travels> travels = travelsMapper.selectPage(page, queryWrapper).getRecords();

		// 组织travelInfo
		List<TravelInfo> travelInfos = getTravelInfo(travels);
		travelsVO.setNum(travelInfos.size());

		// 需要总页数 totalCounts/nums -> 0 + 1 = 1
		// 每页10条，我现在有6条 -> 1
		int totalCounts = travelsMapper.selectCount(queryWrapper);
		int totalPages = (totalCounts / nums) + 1;

		travelsVO.setRows(travelInfos);
		travelsVO.setTotalPage(totalPages);
		travelsVO.setNowPage(nowPage);

		return travelsVO;
	}

	private List<TravelInfo> getTravelInfo(List<Travels> travels) {
		List<TravelInfo> travelInfos = new ArrayList<>();
		for (Travels travel : travels) {
			Student student = studentMapper.selectById(travel.getInitId());
			if (student != null) {
				TravelInfo travelInfo = new TravelInfo();
				travelInfo.setTravelid(travel.getTravelid());
				travelInfo.setStuId(student.getStuId());
				travelInfo.setStuNick(student.getStuNick());
				travelInfo.setStuSex(student.getStuSex());
				travelInfo.setSchool(student.getSchool());
				travelInfo.setOriginAddr(travel.getOriginAddr());
				travelInfo.setDestination(travel.getDestination());
				travelInfo.setStarttime(travel.getStarttime());
				travelInfo.setStuImage(student.getStuImage());
				travelInfos.add(travelInfo);
			}
		}
		return travelInfos;
	}

	@Override
	public Travels getTravel(Long travelid) {
		QueryWrapper<Travels> qw = new QueryWrapper<>();
		qw.eq("travelid", travelid);
		return travelsMapper.selectOne(qw);
	}

	@Override
	public PagedResult getMyTravels(int nums, int nowPage, String originAddr, String destination, String stuSex,
			Integer actualNum, String myId) {
		PagedResult travelsVO = new PagedResult();

		// 先去traveldetail表查询自己所有的travelid
		QueryWrapper<Traveldetail> wrapper = new QueryWrapper<>();
		wrapper.eq("stu_id", myId);
		List<Traveldetail> detail_list = traveldetailMapper.selectList(wrapper);
		List<Long> trid_list = new ArrayList<>();
		for (Traveldetail traveldetail : detail_list) {
			trid_list.add(traveldetail.getTravelid());
		}
		// 行程的限制条件，status为“2”即组队状态
		QueryWrapper<Travels> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status", 2);
		queryWrapper.orderByDesc("created");
		if(trid_list.size()!=0) {
			queryWrapper.in("travelid", trid_list);
			Page<Travels> page = new Page<>(nowPage, nums);
			List<Travels> travels = travelsMapper.selectPage(page, queryWrapper).getRecords();

			// 组织travelInfo
			List<TravelInfo> travelInfos = getTravelInfo(travels);
			travelsVO.setNum(travelInfos.size());

			// 需要总页数 totalCounts/nums -> 0 + 1 = 1
			// 每页10条，我现在有6条 -> 1
			int totalCounts = travelsMapper.selectCount(queryWrapper);
			int totalPages = (totalCounts / nums) + 1;

			travelsVO.setRows(travelInfos);
			travelsVO.setTotalPage(totalPages);
			travelsVO.setNowPage(nowPage);

			return travelsVO;
		}else {
			return null;
		}

	}

	@Override
	public boolean updateTravle(Travels travel) {
		if (travelsMapper.updateById(travel) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int updateTravleStatus(Long travelid, Integer status) {
		Travels travel = travelsMapper.selectById(travelid);
		travel.setStatus(status);
		int isSuccess = travelsMapper.updateById(travel);
		return isSuccess;
	}

}
