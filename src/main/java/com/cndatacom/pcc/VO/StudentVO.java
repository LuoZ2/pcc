package com.cndatacom.pcc.VO;

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

public class StudentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆的账号
     */
   
    private String stuId;

    /**
     * 真实姓名
     */

    private String stuName;

    /**
     * 昵称
     */

    private String stuNick;

    /**
     * 学校
     */

    private String school;

    /**
     * 性别
     */

    private String stuSex;

    /**
     * 密码
     */

    private String stuPassword;

    /**
     * 状态码
     */

    private Integer stuStatus;

    /**
     * 被投诉次数
     */

    private Integer stuNum;

    /**
     * 微信号
     */

    private String stuWechat;

    /**
     * 电话号码
     */

    private String stuTel;

    /**
     * 头像url
     */

    private String stuImage;

    /**
     * 注册时间
     */

    private Date created;
    
    private String userToken;


}
