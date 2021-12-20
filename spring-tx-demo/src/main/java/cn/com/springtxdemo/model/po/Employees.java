package cn.com.springtxdemo.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
@TableName("employees")
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "employee_id", type = IdType.ASSIGN_UUID)
    private String employeeId;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("email")
    private String email;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("hire_date")
    private LocalDate hireDate;

    @TableField("job_id")
    private String jobId;

    @TableField("salary")
    private Double salary;

    @TableField("commission_pct")
    private Double commissionPct;

    @TableField("manager_id")
    private Integer managerId;

    @TableField("department_id")
    private Integer departmentId;


}
