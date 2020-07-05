package com.zys.bookshelf.manager.controller;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.common.PoiUtils;
import com.zys.bookshelf.manager.common.TrendUtils;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.dto.TableRecordDTO;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.Category;
import com.zys.bookshelf.manager.service.BookService;
import com.zys.bookshelf.manager.service.CategoryService;
import com.zys.bookshelf.manager.service.DictionaryService;
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
 * @description Book控制器
 * @create 2020-03-28 16:50
 **/
@RestController
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private TrendUtils trendUtils;

    @GetMapping("/books")
    public PageInfo<Book> page(int pageNum, int pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) String code
            , @RequestParam(required = false) String callNo,@RequestParam(required = false) String author, @RequestParam(required = false) String publisher,
              @RequestParam(required = false) String  categoryCode){
        pageNum = pageNum < 0 ? 1 : pageNum;
        pageSize = pageSize < 0 ? 10 : pageSize;
        Book book = new Book();
        book.setName(name);
        book.setCode(code);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCallNo(callNo);
        Category category = new Category();
        category.setCode(categoryCode);
        book.setCategory(category);
        return bookService.page(pageNum, pageSize, book);
    }

    @PostMapping("/book")
    public BaseResult addBook(@Valid @RequestBody Book book, BindingResult bindingResult){
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
        return bookService.addBook(book);
    }

    @PutMapping("/book")
    public BaseResult updateBook(@Valid @RequestBody Book book, BindingResult bindingResult){
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
        return bookService.updateBook(book);
    }

    @DeleteMapping("/book")
    public BaseResult deleteBook(String ids){
        return bookService.deleteBook(ids.split(","));
    }

    @GetMapping("/book/export")
    public ResponseEntity<byte[]> exportBooks(){
        return PoiUtils.exportBook2Excel(bookService.selectAll());
    }

    @PostMapping("/book/import")
    public BaseResult importBook(MultipartFile file) {
        List<Book> books = PoiUtils.importBook2List(file);
        if (bookService.addBooks(books) == books.size()){
            return BaseResult.success("导入成功！");
        }
        return BaseResult.fail("导入失败！");
    }

    @GetMapping("/book/catcount")
    public List<CategoryCountDTO> getCountByCategory(){
        return bookService.getCountByCategory();
    }
    @GetMapping("/book/years")
    public List<String> getAllYears(){
        return bookService.getAllYears();
    }
    @GetMapping("/book/newbook")
    public List<CategoryCountDTO> getNewBookCountByYear(String year){
        return bookService.getNewBookCountByYear(year);
    }

    @GetMapping("/book/trend/newbook")
    public List<TableRecordDTO> newBookTrend(){
        return trendUtils.newBookTrend();
    }
}