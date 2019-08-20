package com.cndatacom.pcc.domain;

import java.time.LocalDateTime;
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
 * 
 * </p>
 *
 * @author luozhongqian
 * @since 2019-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆的账号
     */
    @TableId("stu_id")
    private String stuId;

    /**
     * 真实姓名
     */
    @TableField("stu_name")
    private String stuName;

    /**
     * 昵称
     */
    @TableField("stu_nick")
    private String stuNick;

    /**
     * 学校
     */
    @TableField("school")
    private String school;

    /**
     * 性别
     */
    @TableField("stu_sex")
    private String stuSex;

    /**
     * 密码
     */
    @TableField("stu_password")
    private String stuPassword;

    /**
     * 状态码
     */
    @TableField("stu_status")
    private Integer stuStatus;

    /**
     * 被投诉次数
     */
    @TableField("stu_num")
    private Integer stuNum;

    /**
     * 微信号
     */
    @TableField("stu_wechat")
    private String stuWechat;

    /**
     * 电话号码
     */
    @TableField("stu_tel")
    private String stuTel;

    /**
     * 头像url
     */
    @TableField("stu_image")
    private String stuImage;

    /**
     * 注册时间
     */
    @TableField("created")
    private Date created;


}
