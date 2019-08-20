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
@TableName("traveldetail")
public class Traveldetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("itemid")
    private Long itemid;
    
    @TableField("travelid")
    private Long travelid;
    
    //出行人
    @TableField("stu_id")
    private String stuId;
    
    //起始地
    @TableField("origin_addr")
    private String originAddr;
    
    //目的地
    @TableField("destination")
    private String destination;
    
    //出发时间
    @TableField("starttime")
    private Date starttime;
    
    //紧急联系人电话
    @TableField("telephone")
    private String telephone;
    
    //加入时间
    @TableField("created")
    private Date created;
    
    //状态码
    @TableField("status")
    private Integer status;
}
