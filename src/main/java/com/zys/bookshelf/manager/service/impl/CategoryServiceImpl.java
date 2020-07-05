package com.zys.bookshelf.manager.service.impl;

import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.mapper.CategoryMapper;
import com.zys.bookshelf.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 21:02
 **/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }
}