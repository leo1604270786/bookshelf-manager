package com.zys.bookshelf.manager.controller;

import com.zys.bookshelf.manager.entity.Dictionary;
import com.zys.bookshelf.manager.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 18:49
 **/
@RestController
@CrossOrigin
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/dicts")
    public List<Dictionary> getDicts(@RequestParam("type") String type){
        return dictionaryService.getDictionaryByType(type);
    }
}