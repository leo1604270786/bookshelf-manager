package com.zys.bookshelf.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.BookItem;
import com.zys.bookshelf.manager.mapper.BookItemMapper;
import com.zys.bookshelf.manager.service.BookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-14 22:36
 **/
@Transactional
@Service
public class BookItemServiceImpl implements BookItemService {
    @Autowired
    private BookItemMapper bookItemMapper;

    @Override
    public PageInfo<BookItem> page(int pageNum, int pageSize, BookItem bookItem) {
        //封装分页参数
        BookItem param = bookItem;
        if (bookItem == null){
            param = new BookItem();
        }
        //开始分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<BookItem> pageInfo = new PageInfo<>(bookItemMapper.selectByCondition(param));

        return pageInfo;
    }

    @Override
    public BaseResult addBookItem(BookItem bookItem) {
        try {
            int insert = bookItemMapper.insertBookItem(bookItem);
            if (insert != 1){
                return BaseResult.fail("添加图书复本信息失败！");
            }
            return BaseResult.success("添加图书复本信息成功！");
        }catch (DuplicateKeyException e){
            return BaseResult.fail("添加图书复本信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("添加图书复本信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateBookItem(BookItem bookItem) {
        try {
            int update = bookItemMapper.updateBookItem(bookItem);
            if (update != 1){
                return BaseResult.fail("更新图书复本信息失败！");
            }
            return BaseResult.success("更新图书复本信息成功！");
        }catch (DuplicateKeyException e){
            return BaseResult.fail("更新图书复本信息失败！与现有数据冲突！");
        } catch (Exception e){
            return BaseResult.fail("更新图书复本信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult deleteBookItem(String[] ids) {
        try{
            int count = 0;
            for (String id : ids) {
                int i = bookItemMapper.deleteByPrimaryKey(id);
                count += i;
            }
            if (count != ids.length){
                return BaseResult.fail("删除图书复本信息失败！");
            }
            return BaseResult.success("删除图书复本信息成功！");
        } catch (Exception e){
            return BaseResult.fail("删除图书复本信息失败！请重试！");
        }
    }

    @Override
    public List<BookItem> selectAll() {
        return bookItemMapper.selectAllBookItems();
    }

    @Override
    public int addBookItems(List<BookItem> bookItems) {
        return bookItemMapper.insertBookItems(bookItems);
    }
}