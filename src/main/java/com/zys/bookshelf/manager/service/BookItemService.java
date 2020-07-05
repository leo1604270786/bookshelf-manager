package com.zys.bookshelf.manager.service;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.entity.BookItem;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-14 22:36
 **/
public interface BookItemService {
    PageInfo<BookItem> page(int pageNum, int pageSize, BookItem bookItem);
    BaseResult addBookItem(BookItem bookItem);
    BaseResult updateBookItem(BookItem bookItem);
    BaseResult deleteBookItem(String[] ids);
    List<BookItem> selectAll();
    int addBookItems(List<BookItem> bookItems);
}
