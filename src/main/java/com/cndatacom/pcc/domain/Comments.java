package com.cndatacom.pcc.domain;


import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author luozhongqian
 * @since 2019-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("id")
    private String id;
    /**
     * 父评论id
     */
    @TableField("father_comment_id")
    private String fatherCommentId;

    /**
     * 评论哪个人的id
     */
    @TableField("to_user_id")
    private String toUserId;

    /**
     * 行程id
     */
    @TableField("travelid")
    private Long travelid;

    /**
     * 留言者，评论的用户id
     */
    @TableField("from_user_id")
    private String fromUserId;

    /**
     * 评论内容
     */
    @TableField("comment")
    private String comment;
    
    @TableField("create_time")
    private Date createTime;


}
