package com.zys.bookshelf.manager.controller;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.common.PoiUtils;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.BookItem;
import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.service.BookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-22 14:46
 **/
@RestController
@CrossOrigin
public class BookItemController {
    @Autowired
    private BookItemService bookItemService;

    @GetMapping("/bookitems")
    public PageInfo<BookItem> page(int pageNum, int pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) String code
            , @RequestParam(required = false) String callNo, @RequestParam(required = false) String author, @RequestParam(required = false) String publisher,
                                   @RequestParam(required = false) String  propNo){
        pageNum = pageNum < 0 ? 1 : pageNum;
        pageSize = pageSize < 0 ? 10 : pageSize;
        BookItem bookItem = new BookItem();
        Book book = new Book();
        book.setName(name);
        book.setCode(code);
        book.setAuthor(author);
        book.setPublisher(publisher);
        bookItem.setCallNo(callNo);
        bookItem.setPropNo(propNo);
        bookItem.setBook(book);
        return bookItemService.page(pageNum, pageSize, bookItem);
    }

    @PostMapping("/bookitem")
    public BaseResult addBookItem(@Valid @RequestBody BookItem bookItem, BindingResult bindingResult){
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
        //通过
        return bookItemService.addBookItem(bookItem);
    }

    @PutMapping("/bookitem")
    public BaseResult updateBookItem(@Valid @RequestBody BookItem bookItem, BindingResult bindingResult){
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
        return bookItemService.updateBookItem(bookItem);
    }

    @DeleteMapping("/bookitem")
    public BaseResult deleteBookItem(String ids){
        return bookItemService.deleteBookItem(ids.split(","));
    }

    @GetMapping("/bookitem/export")
    public ResponseEntity<byte[]> exportBooks(){
        return null;
    }

//    @PostMapping("/bookitem/import")
//    public BaseResult importBook(MultipartFile file) {
//        List<Book> books = PoiUtils.importBook2List(file,categoryService.selectAll(),dictionaryService.getDictionaryByType("bookStatus"));
//        if (bookService.addBooks(books) == books.size()){
//            return BaseResult.success("导入成功！");
//        }
//        return BaseResult.fail("导入失败！");
//    }
}