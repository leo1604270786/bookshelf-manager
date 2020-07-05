package com.zys.bookshelf.manager.service;

import com.zys.bookshelf.manager.entity.Dictionary;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 18:32
 **/
public interface DictionaryService {
    List<Dictionary> getDictionaryByType(String type);
}
