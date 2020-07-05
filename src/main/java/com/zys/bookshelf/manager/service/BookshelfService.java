package com.zys.bookshelf.manager.service;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.BookshelfDistDTO;
import com.zys.bookshelf.manager.dto.CategoryBookshelfDTO;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Bookshelf;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-01 20:46
 **/
public interface BookshelfService {
    PageInfo<Bookshelf> page(int pageNum, int pageSize);
    List<String> getAllPlace();
    List<Bookshelf> getByPlace(String place);
    BaseResult addBookshelf(Bookshelf bookshelf);
    BaseResult updateBookshelf(Bookshelf bookshelf);
    BaseResult deleteBookshelf(String[] ids);
    List<CategoryCountDTO> getBookshelfCount();
    Bookshelf getByBookCallNo(String callNo);
    int addBookshelves(List<Bookshelf> bookshelves);
    List<BookshelfDistDTO> getDist();
    List<CategoryBookshelfDTO> getBookshelfByCategory();
}
