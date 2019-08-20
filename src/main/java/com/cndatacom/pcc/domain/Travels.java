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
@TableName("travels")
public class Travels implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("travelid")
    private Long travelid;

    /**
     * 发起人id
     */
    @TableField("init_id")
    private String initId;

    /**
     * 起始地
     */
    @TableField("origin_addr")
    private String originAddr;

    /**
     * 目的地
     */
    @TableField("destination")
    private String destination;

    /**
     * 预计人数
     */
    @TableField("expect_num")
    private Integer expectNum;

    /**
     * 当前组队人数
     */
    @TableField("actual_num")
    private Integer actualNum;

    /**
     * 状态码
     */
    @TableField("status")
    private Integer status;

    /**
     * 紧急联系人
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;
    
    /**
     * 出发时间
     */
    @TableField("starttime")
    private Date starttime;


}
