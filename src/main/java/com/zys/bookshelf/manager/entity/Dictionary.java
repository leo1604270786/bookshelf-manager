package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@Table(name = "dictionary")
@ToString
@Data
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 值
     */
    private String value;

}