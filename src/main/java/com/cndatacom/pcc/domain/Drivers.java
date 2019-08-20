package com.cndatacom.pcc.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class Drivers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 身份号码
     */
    @TableId("dr_id")
    private String drId;

    /**
     * 司机姓名
     */
    @TableField("dr_name")
    private String drName;

    /**
     * 电话号码
     */
    @TableField("dr_tel")
    private String drTel;

    /**
     * 驾照图片
     */
    @TableField("dr_license")
    private String drLicense;

    /**
     * 车牌
     */
    @TableField("dr_carid")
    private String drCarid;

    /**
     * 车辆型号
     */
    @TableField("dr_cartype")
    private String drCartype;

    /**
     * 家庭住址
     */
    @TableField("dr_addr")
    private String drAddr;

    /**
     * 登陆密码
     */
    @TableField("dr_password")
    private String drPassword;

    /**
     * 状态码
     */
    @TableField("dr_status")
    private Integer drStatus;

    /**
     * 被投诉次数
     */
    @TableField("dr_num")
    private Integer drNum;

    /**
     * 注册时间
     */
    @TableField("created")
    private Date created;


}
