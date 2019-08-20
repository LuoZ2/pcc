package com.cndatacom.pcc.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.PackageHelper;
import com.cndatacom.pcc.VO.CommentsVO;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.dao.CommentsMapper;
import com.cndatacom.pcc.dao.CommentsMapperCustom;
import com.cndatacom.pcc.domain.Comments;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.CommentService;
import com.cndatacom.pcc.util.TimeAgoUtils;
import com.cndatacom.pcc.util.UUIDUtils;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentsMapper commentsMapper;
	
	@Autowired CommentsMapperCustom commentMapperCustom;
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComment(Comments comment) {
		comment.setId(UUIDUtils.createUUID2());
		comment.setCreateTime(new Date());
		commentsMapper.insert(comment);
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public IPage<CommentsVO>  getAllComments(Long travelid, Integer CurrentPage, Integer pageSize) {
		
		Page<CommentsVO> page = new Page<>(CurrentPage, pageSize);
		List<CommentsVO> list = commentMapperCustom.queryComments(page,travelid);
		for (CommentsVO c : list) {
			String timeAgo = TimeAgoUtils.format(c.getCreateTime());
			c.setTimeAgoStr(timeAgo);
		}
		page.setRecords(list);
		return page;
	}
}
