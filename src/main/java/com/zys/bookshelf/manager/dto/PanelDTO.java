package com.zys.bookshelf.manager.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-10 23:26
 **/
@Data
public class PanelDTO implements Serializable {
    private Integer bookCount;
    private Integer userCount;
    private Integer borrowCount;
    private Integer bookshelfCount;
}