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
@TableName("evaluation")
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("pj_id")
    private Long pjId;

    /**
     * 给司机打分
     */
    @TableField("pj_score")
    private Integer pjScore;

    /**
     * 评价那张订单
     */
    @TableField("oid")
    private Long oid;

    /**
     * 司机id
     */
    @TableField("dr_id")
    private String drId;

    /**
     * 哪个人评价
     */
    @TableField("stu_id")
    private String stuId;

    /**
     * 导航是否准(0，1）
     */
    @TableField("pj_navigation")
    private Integer pjNavigation;

    /**
     * 是否礼貌
     */
    @TableField("pj_polite")
    private Integer pjPolite;

    /**
     * 是否平稳安全
     */
    @TableField("pj_security")
    private Integer pjSecurity;

    /**
     * 是否整洁无异味
     */
    @TableField("pj_clean")
    private Integer pjClean;

    /**
     * 评价时间
     */
    @TableField("created")
    private Date created;


}
