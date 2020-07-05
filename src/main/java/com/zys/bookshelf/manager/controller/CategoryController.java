package com.zys.bookshelf.manager.controller;

import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 21:03
 **/
@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/cats")
    public List<Category> getCats(){
        return categoryService.selectAll();
    }
}