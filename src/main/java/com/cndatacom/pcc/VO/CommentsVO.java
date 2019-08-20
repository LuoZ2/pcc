package com.cndatacom.pcc.VO;

import java.util.Date;
import lombok.Data;
@Data
public class CommentsVO {
    private String id;

    /**
     * 行程id
     */
    private Long travelid;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    private Date createTime;

    /**
     * 评论内容
     */
    private String comment;
    
    private String faceImage;
    private String nickname;
    private String toNickname;
    private String timeAgoStr;
    

}