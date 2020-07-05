package com.zys.bookshelf.manager.mapper;

import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Borrow;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.MyMapper;

import java.util.List;

public interface BorrowMapper extends MyMapper<Borrow> {
    List<Borrow> selectByCondition(@Param("borrow") Borrow borrow);
    int insertBorrow(@Param("borrow") Borrow borrow);
    int updateBorrow(@Param("borrow") Borrow borrow);
    int insertBorrows(@Param("borrows") List<Borrow> borrows);

    List<CategoryCountDTO> getBorrowCountByYear(String year);
}