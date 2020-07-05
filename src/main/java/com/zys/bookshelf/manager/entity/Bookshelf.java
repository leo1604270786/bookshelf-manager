package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "bookshelf")
@ToString
@Data
public class Bookshelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "书架编号不能为空")
    private String code;
    /**
     * 架标始
     */
    @Column(name = "label_begin")
    @NotNull(message = "架标始不能为空")
    private String labelBegin;

    /**
     * 架标末
     */
    @Column(name = "label_end")
    @NotNull(message = "架标末不能为空")
    private String labelEnd;

    /**
     * 地点
     */
    @NotNull(message = "地点不能为空")
    private String place;
    @Column(name = "init_capacity")
    @NotNull(message = "初始容量不能为空")
    private Integer initCapacity;

    @Column(name = "left_capacity")
    @NotNull(message = "剩余容量不能为空")
    private Integer leftCapacity;
    @NotNull(message = "X坐标不能为空")
    private Integer x;
    @NotNull(message = "Y坐标不能为空")
    private Integer y;
    @NotNull(message = "Z坐标不能为空")
    private Integer z;

    private Category category;

    /**
     * 更新日期
     */
    @Column(name = "update_date")
    private Date updateDate;

}