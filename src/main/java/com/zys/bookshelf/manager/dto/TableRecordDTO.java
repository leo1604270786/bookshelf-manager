package com.zys.bookshelf.manager.dto;

import com.zys.bookshelf.manager.entity.Category;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-17 19:08
 **/
@Data
@ToString
public class TableRecordDTO implements Serializable {

    //分类
    private Category category;
    //年份及数量
    private YearCountDTO year1;
    private YearCountDTO year2;
    private YearCountDTO year3;
    private YearCountDTO year4;
    private YearCountDTO year5;
    //均值
    private String avg;
}