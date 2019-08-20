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
@TableName("orderdetail")
public class Orderdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "itemid", type = IdType.AUTO)
    private Long itemid;

    /**
     * 属于哪张订单
     */
    @TableField("oid")
    private Long oid;

    /**
     * 出行人
     */
    @TableField("follow_id")
    private String followId;

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
     * 紧急联系人电话
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;


}
