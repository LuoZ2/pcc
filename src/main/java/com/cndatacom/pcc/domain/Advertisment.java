package com.cndatacom.pcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
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
@TableName("advertisment")
public class Advertisment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "adver_id", type = IdType.AUTO)
    private Long adverId;

    /**
     * 商家店铺的名称
     */
    @TableField("adver_name")
    private String adverName;

    /**
     * 商家店铺的名称
     */
    @TableField("adver_image")
    private String adverimage;
    /**
     * 店铺地址
     */
    @TableField("adver_addr")
    private String adverAddr;

    /**
     * 店铺联系电话
     */
    @TableField("adver_tel")
    private String adverTel;

    /**
     * 店铺联系人姓名
     */
    @TableField("header")
    private String header;

    /**
     * 店铺相关描述
     */
    @TableField("adver_pesc")
    private String adverPesc;

    /**
     * 广告价格
     */
    @TableField("money")
    private Integer money;

    /**
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;

    @TableField("adver_status")
    private Integer adverStatus;

    /**
     * 广告投放时长单位月
     */
    @TableField("timelen")
    private Integer timelen;


}
