package com.zys.bookshelf.manager.mapper;

import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Bookshelf;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.MyMapper;

import java.util.List;

public interface BookshelfMapper extends MyMapper<Bookshelf> {
    List<String> getAllPlace();
    List<Bookshelf> getByPlace(String place);
    List<Bookshelf> selectAll();
    int insertBookshelf(Bookshelf bookshelf);
    int insertBookshelves(@Param("bookshelves") List<Bookshelf> bookshelves);
    int updateBookshelf(Bookshelf bookshelf);
    List<CategoryCountDTO> getBookshelfCount();
    Bookshelf getByBookCallNo(String callNo);
    List<String> getBookshelfByCategory(String catCode);
}