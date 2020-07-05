package com.zys.bookshelf.manager.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-12 16:32
 **/
@Data
@ToString
public class YearCountDTO {
    private String year;
    private Integer count;
}