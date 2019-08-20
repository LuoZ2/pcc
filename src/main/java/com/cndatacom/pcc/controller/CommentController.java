 package com.cndatacom.pcc.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cndatacom.pcc.VO.CommentsVO;
import com.cndatacom.pcc.VO.PagedResult;
import com.cndatacom.pcc.VO.ResponseVO;
import com.cndatacom.pcc.VO.TravelInfo;
import com.cndatacom.pcc.domain.Comments;
import com.cndatacom.pcc.domain.Travels;
import com.cndatacom.pcc.service.CommentService;
import com.cndatacom.pcc.service.TravelsService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	/**
	 * 保存视频的评论信息
	 * @param comment 评论实体类
	 * @return  
	 */
	@PostMapping("/comment/saveComment")
	public ResponseVO saveComment(@RequestBody Comments comment, 
			String fatherCommentId, String toUserId) {
		if (StringUtils.isNotBlank(fatherCommentId) && StringUtils.isNotBlank(toUserId)) {
			comment.setFatherCommentId(fatherCommentId);
			comment.setToUserId(toUserId);
		}
		commentService.saveComment(comment);
		return ResponseVO.success("评论成功");
	}
	
	/**
	 * 分页查询视频评论列表，时间顺序倒序排序
	 * @param travelid  行程的id
	 * @param page     当前页数
	 * @param pageSize  每页显示几条数据
	 * @return list集合
	 */
	@PostMapping("/comment/getComments")
	public ResponseVO getVideoComments(@RequestParam Long travelid, Integer page, Integer pageSize) {
		if (travelid == null) {
			return ResponseVO.serviceFail("行程id为空");
		}
		
		
		if (page == null) {
			page = 1;
		}
		
		if (pageSize == null) {
			pageSize = 5;
		}
		IPage<CommentsVO> list = commentService.getAllComments(travelid, page, pageSize);
		return ResponseVO.success(list);
	}
	
}
