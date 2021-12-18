package cn.com.springtxdemo.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zhang Guang Ming
 * @since 2021-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("departments")
public class Departments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "department_id", type = IdType.ASSIGN_UUID)
    private String departmentId;

    @TableField("department_name")
    private String departmentName;

    @TableField("manager_id")
    private Integer managerId;

    @TableField("location_id")
    private Integer locationId;


}
