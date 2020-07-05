package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@Table(name = "category")
@ToString
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类编号
     */
    private String code;

    /**
     * 分类名称
     */
    private String name;

}