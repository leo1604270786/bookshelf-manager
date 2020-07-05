package com.zys.bookshelf.manager.dto;

import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description User传输对象
 * @create 2020-03-26 20:33
 **/
@ToString
public class UserDTO implements Serializable {
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
    @NotNull(message = "性别不能为空")
    private String gender;

    /**
     * 电话
     */
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
    private String role;

    /**
     * 注册日期
     */
    private Date registryDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }
}