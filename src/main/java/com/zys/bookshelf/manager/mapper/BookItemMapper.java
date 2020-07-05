package com.zys.bookshelf.manager.mapper;

import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.BookItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.MyMapper;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-14 22:35
 **/
public interface BookItemMapper extends MyMapper<BookItem> {
    List<BookItem> selectByCondition(@Param("bookitem") BookItem bookitem);
    int insertBookItem(@Param("bookitem")BookItem bookitem);
    int updateBookItem(@Param("bookitem")BookItem bookitem);
    List<BookItem> selectAllBookItems();
    int insertBookItems(@Param("bookitems")List<BookItem> bookitems);
    BookItem getByPropNo(@Param("propNo")String propNo);
}
