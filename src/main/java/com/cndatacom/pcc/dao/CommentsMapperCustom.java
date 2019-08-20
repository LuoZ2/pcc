package com.cndatacom.pcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cndatacom.pcc.VO.CommentsVO;
import com.cndatacom.pcc.domain.Comments;


public interface CommentsMapperCustom extends BaseMapper<Comments> {
	
	public List<CommentsVO> queryComments(Page<CommentsVO> page,@Param("travelid")Long travelid);

	public Integer getComments(Long travelid);
}