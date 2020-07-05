package com.zys.bookshelf.manager.service;

import com.zys.bookshelf.manager.entity.Category;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 21:01
 **/
public interface CategoryService {
    List<Category> selectAll();
}
