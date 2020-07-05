package com.zys.bookshelf.manager.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-31 20:35
 **/
@Data
public class CategoryCountDTO implements Serializable {
    //分类编号
    private String catCode;
    //分类名
    private String category;
    //数量
    private Integer count;
    //借阅量
    private Integer borrowTimes;
}