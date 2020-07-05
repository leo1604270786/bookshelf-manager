package com.zys.bookshelf.manager.service;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.entity.Borrow;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-30 19:37
 **/
public interface BorrowService {
    PageInfo<Borrow> page(int pageNum,int pageSize, Borrow borrow);
    BaseResult addBorrow(Borrow borrow);
    BaseResult updateBorrow(Borrow borrow);
    BaseResult deleteBorrow(String[] ids);
    int addBooks(List<Borrow> borrows);
}
