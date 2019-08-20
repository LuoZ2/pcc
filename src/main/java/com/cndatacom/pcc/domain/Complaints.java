package com.cndatacom.pcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("complaints")
public class Complaints implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "com_id", type = IdType.AUTO)
    private Long comId;

    /**
     * 投诉人id
     */
    @TableField("from_id")
    private String fromId;

    /**
     * 被投诉人id
     */
    @TableField("to_id")
    private String toId;

    /**
     * 被投诉人的身份
     */
    @TableField("roleid")
    private Integer roleid;

    /**
     * 证明材料
     */
    @TableField("material")
    private String material;

    /**
     * 投诉的原因
     */
    @TableField("notes")
    private String notes;
    
    /**
     * 投诉状态码
     */
    @TableField("status")
    private Integer status;

    /**
     * 投诉时间
     */
    @TableField("created")
    private Date created;


}
