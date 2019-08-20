package com.cndatacom.pcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Drivercontact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "drcontact_id", type = IdType.AUTO)
    private Long drcontactId;

    /**
     * 学生id
     */
    @TableField("stu_id")
    private String stuId;

    /**
     * 司机id
     */
    @TableField("dr_id")
    private String drId;


}
