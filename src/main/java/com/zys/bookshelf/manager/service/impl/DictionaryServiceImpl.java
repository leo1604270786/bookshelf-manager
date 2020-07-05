package com.zys.bookshelf.manager.service.impl;

import com.zys.bookshelf.manager.entity.Dictionary;
import com.zys.bookshelf.manager.mapper.DictionaryMapper;
import com.zys.bookshelf.manager.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 18:34
 **/
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Override
    public List<Dictionary> getDictionaryByType(String type) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("type", type);
        List<Dictionary> dictionaries = dictionaryMapper.selectByExample(example);
        return dictionaries;
    }
}