package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-14 22:21
 **/
@Table(name = "location")
@Data
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "地点编号不能为空")
    private String code;
    @NotNull(message = "地点名称不能为空")
    private String name;

    @Column(name = "short_name")
    @NotNull(message = "地点简称不能为空")
    private String shortName;
    @NotNull(message = "所属部门不能为空")
    private String dept;

    @Column(name = "reg_date")
    private Date regDate;
    @Column(name = "upt_date")
    private Date uptDate;
}