package com.zys.bookshelf.manager.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-05-12 14:13
 **/
@Data
public class CategoryBookshelfDTO implements Serializable {
    //分类编号
    private String catCode;
    //分类名
    private String category;
    //占用书架
    private String bookshelves;
}