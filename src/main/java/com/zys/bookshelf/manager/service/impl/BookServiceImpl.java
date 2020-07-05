package com.zys.bookshelf.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.mapper.BookMapper;
import com.zys.bookshelf.manager.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-28 15:45
 **/
@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageInfo<Book> page(int pageNum, int pageSize, Book book) {
        //封装分页参数
        Book param = book;
        if (book == null){
            param = new Book();
        }
        //开始分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Book> pageInfo = new PageInfo<>(bookMapper.selectByCondition(param));

        return pageInfo;
    }

    @Override
    public List<Book> selectByCondition(Book book) {
        return bookMapper.selectByCondition(book);
    }

    @Override
    public BaseResult addBook(Book book) {
        try {
            int insert = bookMapper.insertBook(book);
            if (insert != 1){
                return BaseResult.fail("添加图书信息失败！");
            }
            return BaseResult.success("添加图书信息成功！");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("添加图书信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("添加图书信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateBook(Book book) {
        try {
            int update = bookMapper.updateBook(book);
            if (update != 1){
                return BaseResult.fail("更新图书信息失败！");
            }
            return BaseResult.success("更新图书信息成功！");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("更新图书信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("更新图书信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult deleteBook(String[] ids) {
        try {
            int count = 0;
            for (String id : ids) {
                int i = bookMapper.deleteByPrimaryKey(id);
                count += i;
            }
            if (count != ids.length){
                return BaseResult.fail("删除图书信息失败！");
            }
            return BaseResult.success("删除图书信息成功！");
        } catch(Exception e){
            return BaseResult.fail("删除图书信息失败！请重试！");
        }
    }

    @Override
    public List<Book> selectAll() {
        return bookMapper.selectAllBooks();
    }

    @Override
    public int addBooks(List<Book> books) {
        return bookMapper.insertBooks(books);
    }

    @Override
    public Book getByCode(String code) {
        return bookMapper.getByCode(code);
    }

    /**
     * 获取各类图书数量与借阅量
     * @return
     */
    @Override
    public List<CategoryCountDTO> getCountByCategory() {
        //获取各类图书数量信息
        List<CategoryCountDTO> countByCategory = bookMapper.getCountByCategory();
        //获取各类图书借阅量信息
        List<CategoryCountDTO> boCountByCategory = bookMapper.getBoCountByCategory();
        int len = countByCategory.size();
        for (int i = 0; i < len; i++) {
            //组合信息
            BeanUtils.copyProperties(countByCategory.get(i), boCountByCategory.get(i),new String[]{"category","borrowTimes"});
        }
        return boCountByCategory;
    }

    @Override
    public List<String> getAllYears() {
        return bookMapper.getAllYears();
    }

    @Override
    public List<CategoryCountDTO> getNewBookCountByYear(String year) {
        return bookMapper.getNewBookCountByYear(year);
    }
}