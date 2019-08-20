package com.cndatacom.pcc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cndatacom.pcc.VO.CommentsVO;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.domain.Comments;

public interface CommentService {
	/**
	 * 保存视频评论信息
	 * @param comment 视频实体类对象
	 * @return 
	 */
	public void saveComment(Comments comment);

	/**
	 * 分页查询视频评论列表，时间顺序倒序排序
	 * @param travelid  行程的id
	 * @param page     当前页数
	 * @param pageSize  每页显示几条数据
	 * @return   list集合
	 */
	public IPage<CommentsVO> getAllComments(Long travelid, Integer page, Integer pageSize);
}
