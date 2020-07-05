package com.zys.bookshelf.manager.common;

import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.dto.TableRecordDTO;
import com.zys.bookshelf.manager.dto.YearCountDTO;
import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.mapper.BookMapper;
import com.zys.bookshelf.manager.mapper.BorrowMapper;
import com.zys.bookshelf.manager.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description 趋势预测工具类
 * @create 2020-04-16 18:44
 **/
@Component
public class TrendUtils {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 新书量预测
     * @return
     */
    public List<TableRecordDTO> newBookTrend(){
        List<Category> categories = categoryMapper.selectAll();
        int[] sums = new int[6];
        List<TableRecordDTO> result = new ArrayList<>();
        //计算年份
        int[] years = new int[]{2015,2016, 2017, 2018, 2019};
        int[] counts = new int[5];
        Map<String,List<CategoryCountDTO>> data = new HashMap<>();
        //循环所有年份 获取数据
        for (int year : years) {
            List<CategoryCountDTO> newBookCountByYear = bookMapper.getNewBookCountByYear(year+""); //某年各类新书量
            data.put(year+"",newBookCountByYear);
        }
        //各个分类
        int len = categories.size();
        for (int i = 0; i < len; i++) {
            TableRecordDTO record = new TableRecordDTO();
            record.setCategory(categories.get(i));
            int j = 0;
            //循环所有年份
            for (int year : years) {
                List<CategoryCountDTO> newBookCountByYear = data.get(year+""); //某年各类新书量
                boolean flag = false;
                YearCountDTO yearCountDTO = new YearCountDTO();
                yearCountDTO.setYear(year+"");
                for (CategoryCountDTO categoryCountDTO : newBookCountByYear) {
                    if(categoryCountDTO.getCatCode().equals(categories.get(i).getCode())){ //存在数据
                        yearCountDTO.setCount(categoryCountDTO.getCount());
                        flag = true;
                        break;
                    }
                }
                if (!flag){ //没有数据
                    yearCountDTO.setCount(0);
                }
                counts[j] = yearCountDTO.getCount();
                sums[j] += counts[j];
                switch (j){
                    case 0: record.setYear1(yearCountDTO); break;
                    case 1: record.setYear2(yearCountDTO); break;
                    case 2: record.setYear3(yearCountDTO); break;
                    case 3: record.setYear4(yearCountDTO); break;
                    case 4: record.setYear5(yearCountDTO); break;
                }
                j++;
            }
            //计算预测均值
            String avg = getTrendAvg(years, counts);
            record.setAvg(avg);
            try{
                sums[5] += Integer.parseInt(avg);
            }catch (Exception e){
                e.printStackTrace();
            }
            result.add(record);
        }
        //添加合计
        TableRecordDTO sumRecord = new TableRecordDTO();
        Category c = new Category();
        c.setCode("合计");
        sumRecord.setCategory(c);
        for (int i = 0; i < years.length; i++) {
            YearCountDTO yearCountDTO = new YearCountDTO();
            yearCountDTO.setCount(sums[i]);
            switch (i){
                case 0: sumRecord.setYear1(yearCountDTO); break;
                case 1: sumRecord.setYear2(yearCountDTO); break;
                case 2: sumRecord.setYear3(yearCountDTO); break;
                case 3: sumRecord.setYear4(yearCountDTO); break;
                case 4: sumRecord.setYear5(yearCountDTO); break;
            }
        }
        sumRecord.setAvg(sums[5] +"");
        result.add(sumRecord);
        return result;
    }

    /**
     * 借阅量预测
     * @return
     */
    public List<TableRecordDTO> borrowTrend(){
        List<Category> categories = categoryMapper.selectAll();
        int[] sums = new int[6];
        List<TableRecordDTO> result = new ArrayList<>();
        //计算年份
        int[] years = new int[]{2015,2016, 2017, 2018, 2019};
        int[] counts = new int[5];
        Map<String,List<CategoryCountDTO>> data = new HashMap<>();
        //循环所有年份 获取数据
        for (int year : years) {
            List<CategoryCountDTO> borrowCountByYear = borrowMapper.getBorrowCountByYear(year + "");//某年各类借阅量
            data.put(year+"",borrowCountByYear);
        }
        //各个分类
        int len = categories.size();
        for (int i = 0; i < len; i++) {
            TableRecordDTO record = new TableRecordDTO();
            record.setCategory(categories.get(i));
            int j = 0;
            //循环所有年份
            for (int year : years) {
                List<CategoryCountDTO> borrowCountByYear = data.get(year+""); //某年各类借阅量
                boolean flag = false;
                YearCountDTO yearCountDTO = new YearCountDTO();
                yearCountDTO.setYear(year+"");
                for (CategoryCountDTO categoryCountDTO : borrowCountByYear) {
                    if(categoryCountDTO.getCatCode().equals(categories.get(i).getCode())){ //存在数据
                        yearCountDTO.setCount(categoryCountDTO.getCount());
                        flag = true;
                        break;
                    }
                }
                if (!flag){ //没有数据
                    yearCountDTO.setCount(0);
                }
                counts[j] = yearCountDTO.getCount();
                sums[j] += counts[j];
                switch (j){
                    case 0: record.setYear1(yearCountDTO); break;
                    case 1: record.setYear2(yearCountDTO); break;
                    case 2: record.setYear3(yearCountDTO); break;
                    case 3: record.setYear4(yearCountDTO); break;
                    case 4: record.setYear5(yearCountDTO); break;
                }
                j++;
            }
            //计算预测均值
            String avg = getTrendAvg(years, counts);
            record.setAvg(avg);
            try{
                sums[5] += Integer.parseInt(avg);
            }catch (Exception e){
                e.printStackTrace();
            }
            result.add(record);
        }
        //添加合计
        TableRecordDTO sumRecord = new TableRecordDTO();
        Category c = new Category();
        c.setCode("合计");
        sumRecord.setCategory(c);
        for (int i = 0; i < years.length; i++) {
            YearCountDTO yearCountDTO = new YearCountDTO();
            yearCountDTO.setCount(sums[i]);
            switch (i){
                case 0: sumRecord.setYear1(yearCountDTO); break;
                case 1: sumRecord.setYear2(yearCountDTO); break;
                case 2: sumRecord.setYear3(yearCountDTO); break;
                case 3: sumRecord.setYear4(yearCountDTO); break;
                case 4: sumRecord.setYear5(yearCountDTO); break;
            }
        }
        sumRecord.setAvg(sums[5] +"");
        result.add(sumRecord);
        return result;
    }

    private String getTrendAvg(int[] years, int[] counts){
        int sumYear = 0, sumCount = 0; //计算总和
        for (int year : years) {
            year -= 2000;
        }
        for (int i = 0; i < years.length; i++) {
            sumYear += years[i];
            sumCount += counts[i];
        }
        //计算均值
        int meanYear = sumYear / years.length;
        int meanCount = sumCount / counts.length;
        //计算协方差
        double cov = 0.0;
        //计算方差
        double varYear = 0.0;
        for (int i = 0; i < years.length; i++) {
            varYear += (years[i] - meanYear) * (years[i] - meanYear);
            cov += (years[i] - meanYear) * (counts[i] - meanCount);
        }
        //得出线性回归方程f(x) = aX +b 的斜率和截距
        double a = cov / varYear;
        double b = meanCount - a * meanYear;

        //预测
        double[] arr = new double[3];
        int lastYear = years[years.length-1];
        arr[0] = a * (lastYear+1) + b;
        arr[1] = a * (lastYear+2) + b;
        arr[2] = a * (lastYear+3) + b;
        int result = Math.abs((int)(arr[0] + arr[1] + arr[2]) / 3);
        return result + "";
    }
}