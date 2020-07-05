package com.zys.bookshelf.manager.service;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Book;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 15:45
 **/
public interface BookService {
    PageInfo<Book> page(int pageNum, int pageSize, Book book);
    List<Book> selectByCondition(Book book);
    BaseResult addBook(Book book);
    BaseResult updateBook(Book book);
    BaseResult deleteBook(String[] ids);
    List<Book> selectAll();
    int addBooks(List<Book> books);
    Book getByCode(String code);
    List<CategoryCountDTO> getCountByCategory();
    List<String> getAllYears();
    List<CategoryCountDTO> getNewBookCountByYear(String year);
}
