package com.zys.bookshelf.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.Borrow;
import com.zys.bookshelf.manager.entity.User;
import com.zys.bookshelf.manager.mapper.BorrowMapper;
import com.zys.bookshelf.manager.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-30 19:38
 **/
@Service
@Transactional
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public PageInfo<Borrow> page(int pageNum, int pageSize, Borrow borrow) {
        Borrow param = borrow;
        if (borrow == null){
            param = new Borrow();
            User reader = new User();
            Book book = new Book();
            param.setReader(reader);
            param.setBook(book);
        }
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Borrow> borrowPageInfo = new PageInfo<>(borrowMapper.selectByCondition(param));
        return borrowPageInfo;
    }

    @Override
    public BaseResult addBorrow(Borrow borrow) {
        try {
            int insert = borrowMapper.insertBorrow(borrow);
            if (insert != 1){
                return BaseResult.fail("添加借阅记录失败！");
            }
            return BaseResult.success("添加借阅记录成功！");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("添加借阅记录失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("添加借阅记录失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateBorrow(Borrow borrow) {
        try {
            int update = borrowMapper.updateBorrow(borrow);
            if (update != 1){
                return BaseResult.fail("更新借阅记录失败！");
            }
            return BaseResult.success("更新借阅记录成功");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("更新借阅记录失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("更新借阅记录失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult deleteBorrow(String[] ids) {
        try {
            int count = 0;
            for (String id : ids) {
                int i = borrowMapper.deleteByPrimaryKey(id);
                count += i;
            }
            if (count != ids.length){
                return BaseResult.fail("删除借阅记录失败！");
            }
            return BaseResult.success("删除借阅记录成功！");
        } catch(Exception e){
            return BaseResult.fail("删除借阅记录失败！请检查数据重新提交！");
        }
    }

    @Override
    public int addBooks(List<Borrow> borrows) {
        return borrowMapper.insertBorrows(borrows);
    }
}