package com.zys.bookshelf.manager.mapper;

import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Book;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

public interface BookMapper extends MyMapper<Book> {
    List<Book> selectByCondition(@Param("book") Book book);
    int insertBook(@Param("book") Book book);
    int updateBook(@Param("book") Book book);
    List<Book> selectAllBooks();
    int insertBooks(@Param("books") List<Book> books);
    Book getByCode(@Param("code") String code);
    List<CategoryCountDTO> getCountByCategory();
    List<CategoryCountDTO> getBoCountByCategory();
    List<String> getAllYears();
    List<CategoryCountDTO> getNewBookCountByYear(String year);
}