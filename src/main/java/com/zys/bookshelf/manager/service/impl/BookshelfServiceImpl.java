package com.zys.bookshelf.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.common.TrendUtils;
import com.zys.bookshelf.manager.dto.*;
import com.zys.bookshelf.manager.entity.Bookshelf;
import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.mapper.BookMapper;
import com.zys.bookshelf.manager.mapper.BookshelfMapper;
import com.zys.bookshelf.manager.mapper.CategoryMapper;
import com.zys.bookshelf.manager.service.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-01 20:48
 **/
@Service
@Transactional
public class BookshelfServiceImpl implements BookshelfService {
    @Autowired
    private BookshelfMapper bookshelfMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TrendUtils trendUtils;
    private static final int CAP = 460; //国家标准书架容量：460册/架
    private static final double LOST_RATE = 0.02; //图书遗失率

    @Override
    public PageInfo<Bookshelf> page(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(bookshelfMapper.selectAll());
    }

    @Override
    public List<String> getAllPlace() {
        return bookshelfMapper.getAllPlace();
    }

    @Override
    public List<Bookshelf> getByPlace(String place) {
        return bookshelfMapper.getByPlace(place);
    }

    @Override
    public BaseResult addBookshelf(Bookshelf bookshelf) {
        try {
            int insert = bookshelfMapper.insertBookshelf(bookshelf);
            if (insert != 1){
                return BaseResult.fail("添加书架信息失败！");
            }
            return BaseResult.success("添加书架信息成功！");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("添加书架信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("添加书架信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateBookshelf(Bookshelf bookshelf) {
        try {
            int update = bookshelfMapper.updateBookshelf(bookshelf);
            if (update != 1){
                return BaseResult.fail("更新书架信息失败！");
            }
            return BaseResult.success("更新书架信息成功！");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("更新书架信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("更新书架信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult deleteBookshelf(String[] ids) {
        try {
            int count = 0;
            for (String id : ids) {
                int i = bookshelfMapper.deleteByPrimaryKey(id);
                count += i;
            }
            if (count != ids.length){
                return BaseResult.fail("删除书架信息失败！");
            }
            return BaseResult.success("删除书架信息成功！");
        } catch (Exception e) {
            return BaseResult.fail("删除书架信息失败，请重试！");
        }
    }

    @Override
    public List<CategoryCountDTO> getBookshelfCount() {
        return bookshelfMapper.getBookshelfCount();
    }

    @Override
    public Bookshelf getByBookCallNo(String callNo) {
        return bookshelfMapper.getByBookCallNo(callNo);
    }

    @Override
    public int addBookshelves(List<Bookshelf> bookshelves) {
        return bookshelfMapper.insertBookshelves(bookshelves);
    }

    @Override
    public List<BookshelfDistDTO> getDist() { //生成架位分配表
        int[] sums = new int[6];
        List<BookshelfDistDTO> result = new ArrayList<>();
        List<TableRecordDTO> newBookTrend = trendUtils.newBookTrend();
        List<TableRecordDTO> borrowTrend = trendUtils.borrowTrend();
        List<CategoryCountDTO> countByCategory = bookMapper.getCountByCategory();
        int len = newBookTrend.size();
        for (int i = 0; i < len-1; i++) {
            BookshelfDistDTO distDTO = new BookshelfDistDTO();
            TableRecordDTO newBookRecord = newBookTrend.get(i);
            TableRecordDTO borrowRecord = borrowTrend.get(i);
            distDTO.setCategory(newBookRecord.getCategory());
            distDTO.setCount(countByCategory.get(i).getCount());
            sums[0] += distDTO.getCount();
            distDTO.setNewBookCount(Integer.parseInt(newBookRecord.getAvg())); //估算年平均新书量
            sums[1] += distDTO.getNewBookCount();
            distDTO.setBorrowCount(Integer.parseInt(borrowRecord.getAvg())); //估算年平均借阅量
            sums[2] += distDTO.getBorrowCount();
            distDTO.setTheoryDist((int)Math.ceil((double) distDTO.getCount() / CAP)); //理论分配架位 = 册数/国家标准书架容量
            sums[3] += distDTO.getTheoryDist();
            distDTO.setTheoryPre((int)Math.ceil((distDTO.getNewBookCount() - distDTO.getBorrowCount()*LOST_RATE) / CAP)); //理论预留架位 = (年均新书量-年均借阅量*图书遗失率)/国家标准书架容量
            sums[4] += distDTO.getTheoryPre();
            distDTO.setResultDist(distDTO.getTheoryDist() + distDTO.getTheoryPre());
            sums[5] += distDTO.getResultDist();
            result.add(distDTO);
        }
        if (bookshelfMapper.selectCount(new Bookshelf()) <= 0){ //没有书架记录
            //生成书架记录
            genBookshelf(result);
        }
        //添加合计
        BookshelfDistDTO sumDTO = new BookshelfDistDTO();
        Category c = new Category();
        c.setName("合计");
        sumDTO.setCategory(c);
        sumDTO.setCount(sums[0]);
        sumDTO.setNewBookCount(sums[1]);
        sumDTO.setBorrowCount(sums[2]);
        sumDTO.setTheoryDist(sums[3]);
        sumDTO.setTheoryPre(sums[4]);
        sumDTO.setResultDist(sums[5]);
        result.add(sumDTO);
        return result;
    }

    @Override
    public List<CategoryBookshelfDTO> getBookshelfByCategory() {
        List<CategoryBookshelfDTO> result = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();
        for (Category category : categories) {
            CategoryBookshelfDTO categoryBookshelfDTO = new CategoryBookshelfDTO();
            StringBuilder sb = new StringBuilder();
            List<String> bookshelfByCategory = bookshelfMapper.getBookshelfByCategory(category.getCode());
            for (int i = 0, len = bookshelfByCategory.size(); i < len; i++) {
                if (i != len-1)
                    sb.append(bookshelfByCategory.get(i)).append(", ");
                else
                    sb.append(bookshelfByCategory.get(i));
            }
            categoryBookshelfDTO.setCatCode(category.getCode());
            categoryBookshelfDTO.setCategory(category.getName());
            categoryBookshelfDTO.setBookshelves(sb.toString());
            result.add(categoryBookshelfDTO);
        }
        return result;
    }

    /**
     * 根据架位分配表生成书架记录
     * @param distDTOS
     */
    private void genBookshelf(List<BookshelfDistDTO> distDTOS){
        List<Bookshelf> bookshelves = new ArrayList<>();
        int codeBegin = 1, placeBegin = 101, initCap = 14, leftCap = 0, num = 0;
        int[] xs = {-30,0,30,60,-30,0,30,60};
        int[] ys = {0,0,0,0,0,0,0,0};
        int[] zs = {-35,-35,-35,-35,35,35,35,35};
        int len = distDTOS.size();
        for (int i = 0; i < len; i++){
            BookshelfDistDTO distDTO = distDTOS.get(i);
            Category category = distDTO.getCategory();
            int resultDist = distDTO.getResultDist();
            for (int j = 0; j < resultDist; j++, codeBegin++,num++){
                Bookshelf bookshelf = new Bookshelf();
                bookshelf.setCategory(category);
                bookshelf.setCode("BS" + String.format("%03d",codeBegin));
                bookshelf.setLabelBegin(category.getCode() + String.format("%03d", 20*j + 1));//
                bookshelf.setLabelEnd(category.getCode() + String.format("%03d", 20*(j+1)));//
                bookshelf.setInitCapacity(initCap);
                bookshelf.setLeftCapacity(leftCap);
                bookshelf.setPlace("借阅室" + (placeBegin + num/8));
                bookshelf.setX(xs[num % 8]);
                bookshelf.setY(ys[num % 8]);
                bookshelf.setZ(zs[num % 8]);
                bookshelf.setUpdateDate(new Date());
                bookshelves.add(bookshelf);
            }
        }
//        System.out.println(bookshelves.size());
//        for (Bookshelf bookshelf : bookshelves) {
//            System.out.println(bookshelf);
//        }
        addBookshelves(bookshelves);
    }
}