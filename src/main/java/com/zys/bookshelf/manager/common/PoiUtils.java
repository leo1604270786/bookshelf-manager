package com.zys.bookshelf.manager.common;

import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.entity.Dictionary;
import com.zys.bookshelf.manager.entity.User;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-27 21:18
 **/
public class PoiUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static ResponseEntity<byte[]> exportUser2Excel(List<User> users) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("用户信息");
            //3.2设置文档管理员
            dsi.setManager("Leo");
            //3.3设置组织机构
            dsi.setCompany("NCU");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("用户信息表");
            //4.2.设置文档标题
            si.setTitle("用户信息");
            //4.3 设置文档作者
            si.setAuthor("Leo");
            //4.4设置文档备注
            si.setComments("无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("用户信息表");
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 30 * 256);//第四列(出生日期那一列)的宽度为10个字符的宽度
            sheet.setColumnWidth(5, 12 * 256);
            sheet.setColumnWidth(6, 16 * 256);
            sheet.setColumnWidth(7, 20 * 256);
            sheet.setColumnWidth(8, 5 * 256);
            sheet.setColumnWidth(9, 12 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("工号/编号");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("姓名");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("性别");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("密码");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("电话号码");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("邮箱");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(7);
            cell7.setCellValue("身份证号");
            cell7.setCellStyle(headerStyle);
            HSSFCell cell8 = headerRow.createCell(8);
            cell8.setCellValue("角色");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(9);
            cell9.setCellValue("注册日期");
            cell9.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < users.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                User user = users.get(i);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getCode());
                row.createCell(2).setCellValue(user.getName());
                row.createCell(3).setCellValue(user.getGender());
                row.createCell(4).setCellValue(user.getPassword());
                row.createCell(5).setCellValue(user.getPhone());
                row.createCell(6).setCellValue(user.getEmail());
                row.createCell(7).setCellValue(user.getIdcard());
                row.createCell(8).setCellValue(user.getRole().getId());
                row.createCell(9).setCellValue(sdf.format(user.getRegistryDate()));
            }
            headers = new HttpHeaders();
            String date = sdf.format(new Date());
            headers.setContentDispositionFormData("user", new String(("用户表"+date+".xls").getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> exportBook2Excel(List<Book> books) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("图书信息");
            //3.2设置文档管理员
            dsi.setManager("Leo");
            //3.3设置组织机构
            dsi.setCompany("NCU");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("图书信息表");
            //4.2.设置文档标题
            si.setTitle("图书信息");
            //4.3 设置文档作者
            si.setAuthor("Leo");
            //4.4设置文档备注
            si.setComments("无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("图书信息表");
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 16 * 256);
            sheet.setColumnWidth(6, 16 * 256);
            sheet.setColumnWidth(7, 30 * 256);
            sheet.setColumnWidth(8, 10 * 256);
            sheet.setColumnWidth(9, 10 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("图书编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("索书号");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("书名");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("作者");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("出版社");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("ISBN");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("出版年份");
            cell6.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < books.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Book book = books.get(i);
                row.createCell(0).setCellValue(book.getCode());
                row.createCell(1).setCellValue(book.getCallNo());
                row.createCell(2).setCellValue(book.getName());
                row.createCell(3).setCellValue(book.getAuthor());
                row.createCell(4).setCellValue(book.getPublisher());
                row.createCell(5).setCellValue(book.getIsbn());
                row.createCell(6).setCellValue(book.getPublishYear());
            }
            headers = new HttpHeaders();
            String date = sdf.format(new Date());
            headers.setContentDispositionFormData("book", new String(("图书表"+date+".xls").getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static List<User> importUser2List(MultipartFile file) {
        List<User> users = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                User user;
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//没数据
                    }
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    user = new User();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellTypeEnum()) {
                            case STRING: {
                                String cellValue = cell.getStringCellValue();
                                if (cellValue == null) {
                                    cellValue = "";
                                }
                                switch (k) {
                                    case 0:
                                        user.setCode(cellValue);
                                        break;
                                    case 1:
                                        user.setName(cellValue);
                                        break;
                                    case 2:
                                        user.setGender(cellValue);
                                        break;
                                    case 3:
                                        user.setPassword(cellValue);
                                        break;
                                    case 4:
                                        user.setPhone(cellValue);
                                        break;
                                    case 5:
                                        user.setEmail(cellValue);
                                        break;
                                    case 6:
                                        user.setIdcard(cellValue);
                                        break;
                                    case 7:
                                        try {
                                            Dictionary dictionary = new Dictionary();
                                            dictionary.setId(Integer.parseInt(cellValue));
                                            user.setRole(dictionary);
                                        } catch (NumberFormatException e) {
                                        }
                                        break;
                                    case 8:
                                        try {
                                            user.setRegistryDate(sdf.parse(cellValue));
                                        } catch (ParseException e) {
                                        }
                                        break;
                                }
                            }
                            break;
                            case NUMERIC:{
                                String cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                                if (k == 4){
                                    user.setPhone(cellValue);
                                }
                                if (k == 6){
                                    user.setIdcard(cellValue);
                                }
                                if (k == 7){
                                    try {
                                        Dictionary dictionary = new Dictionary();
                                        dictionary.setId(Integer.parseInt(cellValue));
                                        user.setRole(dictionary);
                                    } catch (NumberFormatException e) {
                                    }
                                }
                            }
                            break;
                            default: { }
                            break;
                        }
                    }
                    users.add(user);
                }
            }
        } catch (IOException e) {
        }
        return users;
    }

    public static List<Book> importBook2List(MultipartFile file) {
        List<Book> books = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                Book book;
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//没数据
                    }
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    book = new Book();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellTypeEnum()) {
                            case STRING: {
                                String cellValue = cell.getStringCellValue();
                                if (cellValue == null) {
                                    cellValue = "";
                                }
                                switch (k) {
                                    case 0:
                                        book.setCode(cellValue);
                                        break;
                                    case 1:
                                        book.setCallNo(cellValue);
                                        break;
                                    case 2:
                                        book.setName(cellValue);
                                        break;
                                    case 3:
                                        book.setAuthor(cellValue);
                                        break;
                                    case 4:
                                        book.setPublisher(cellValue);
                                        break;
                                    case 5:
                                        book.setIsbn(cellValue);
                                        break;
                                    case 6:
                                        book.setPublishYear(cellValue);
                                        break;
                                }
                            }
                            break;
                            case NUMERIC:{
                                String cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                                if (k == 0){
                                    book.setCode(cellValue);
                                }
                                if (k == 6){
                                    book.setPublishYear(cellValue);
                                }
                            }
                            break;
                            default: { }
                            break;
                        }
                    }
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}