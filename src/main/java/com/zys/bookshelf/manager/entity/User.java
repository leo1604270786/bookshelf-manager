package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "user")
@ToString
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 读者编号或工号
     */
    private String code;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "必须选择性别")
    private String gender;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;

    /**
     * 电话
     */
    @Length(min = 11, max = 11, message = "电话号码长度应为11")
    @NotNull(message = "电话不能为空")
    private String phone;
    /*
    *  邮箱
    * */
    @NotNull(message = "邮箱不能为空")
    private String email;
    /*
    *  身份证号
    * */
    @NotNull(message = "身份证号不能为空")
    private String idcard;

    /**
     * 角色
     */
    private Dictionary role;

    /**
     * 注册日期
     */
    @Column(name = "registry_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registryDate;

}