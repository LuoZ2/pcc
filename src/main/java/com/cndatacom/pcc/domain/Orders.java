package com.cndatacom.pcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
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
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "oid")
    private Long oid;

    /**
     * 发起人
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
     * 出发时间
     */
    @TableField("starttime")
    private Date starttime;

    /**
     * 出行人数
     */
    @TableField("peoplenum")
    private Integer peoplenum;

    /**
     * 预计金额
     */
    @TableField("money")
    private Integer money;

    /**
     * 状态码
     */
    @TableField("ostatus")
    private Integer ostatus;

    /**
     * 哪个司机接单
     */
    @TableField("dr_id")
    private String drId;

    /**
     * 紧急联系人电话
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;

    /**
     * 说明该订单有哪条信息形成
     */
    @TableField("travelid")
    private Long travelid;


}
