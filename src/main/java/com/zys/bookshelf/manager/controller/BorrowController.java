package com.zys.bookshelf.manager.controller;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.common.TrendUtils;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.TableRecordDTO;
import com.zys.bookshelf.manager.entity.*;
import com.zys.bookshelf.manager.service.BookService;
import com.zys.bookshelf.manager.service.BorrowService;
import com.zys.bookshelf.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-30 19:44
 **/
@RestController
@CrossOrigin
public class BorrowController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private TrendUtils trendUtils;

    @GetMapping("/borrows")
    public PageInfo<Borrow> page(int pageNum, int pageSize,
                                 @RequestParam(name = "readercode",required = false) String readerCode,
                                 @RequestParam(name = "readername",required = false) String readerName,
                                 @RequestParam(name = "bookcode",required = false) String bookCode,
                                 @RequestParam(name = "bookname",required = false) String bookName,
                                 @RequestParam(name = "borrowstatus",required = false) String borrowStatus,
                                 @RequestParam(name = "callNo",required = false) String callNo){
        pageNum = pageNum < 0 ? 1 : pageNum;
        pageSize = pageSize < 0 ? 10 : pageSize;
        Borrow borrow = new Borrow();
        User reader = new User();
        reader.setCode(readerCode);
        reader.setName(readerName);
        Book book = new Book();
        book.setCode(bookCode);
        book.setName(bookName);
        if (borrowStatus != null && !"".equals(borrowStatus)){
            Dictionary dictionary = new Dictionary();
            dictionary.setId(Integer.parseInt(borrowStatus));
            borrow.setStatus(dictionary);
        }
        borrow.setCallNo(callNo);
        borrow.setReader(reader);
        borrow.setBook(book);
        return borrowService.page(pageNum,pageSize,borrow);
    }

    @PostMapping("/borrow")
    public BaseResult addBorrow(@Valid @RequestBody Borrow borrow, BindingResult bindingResult){
        //数据校验不通过
        if (bindingResult.hasErrors()){
            //拼接错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : allErrors) {
                errorMsg.append(error.getDefaultMessage()).append(",");
            }
            //返回保存失败信息
            return BaseResult.fail(errorMsg.toString());
        }
        User reader = userService.getByCode(borrow.getReader().getCode());
        if (reader == null){
            return BaseResult.fail("读者编号不存在！");
        }
        Book book = bookService.getByCode(borrow.getBook().getCode());
        if (book == null){
            return BaseResult.fail("图书编号不存在！");
        }
        //通过
        borrow.setReader(reader);
        borrow.setBook(book);
        return borrowService.addBorrow(borrow);
    }

    @PutMapping("/borrow")
    public BaseResult updateBorrow(@Valid @RequestBody Borrow borrow, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            //拼接错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : allErrors) {
                errorMsg.append(error.getDefaultMessage()).append(",");
            }
            //返回保存失败信息
            return BaseResult.fail(errorMsg.toString());
        }
        return borrowService.updateBorrow(borrow);
    }

    @DeleteMapping("/borrow")
    public BaseResult deleteBorrow(String ids){
        return borrowService.deleteBorrow(ids.split(","));
    }

    @GetMapping("/borrow/trend")
    public List<TableRecordDTO> borrowTrend(){
        return trendUtils.borrowTrend();
    }
}