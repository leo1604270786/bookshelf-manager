package com.zys.bookshelf.manager.dto;

import com.zys.bookshelf.manager.entity.Category;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description 架位分配
 * @create 2020-04-18 20:33
 **/
@Data
@ToString
public class BookshelfDistDTO implements Serializable {
    //分类
    private Category category;
    //册数
    private int count;
    //估算年均新书量
    private int newBookCount;
    //估算年均借阅量
    private int borrowCount;
    //理论分配架位
    private int theoryDist;
    //理论预留架位
    private int theoryPre;
    //应分配架位
    private int resultDist;
}