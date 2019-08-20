package com.cndatacom.pcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("stucontact")
public class Stucontact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "contactid", type = IdType.AUTO)
    private Long contactid;

    /**
     * 发起人
     */
    @TableField("to_id")
    private String toId;

    /**
     * 接收人
     */
    @TableField("from_id")
    private String fromId;

    /**
     * 状态码
     */
    @TableField("status")
    private Integer status;


}
