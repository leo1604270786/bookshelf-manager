package com.zys.bookshelf.manager.controller;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.BookshelfDistDTO;
import com.zys.bookshelf.manager.dto.CategoryBookshelfDTO;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.entity.Book;
import com.zys.bookshelf.manager.entity.Bookshelf;
import com.zys.bookshelf.manager.service.BookshelfService;
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
 * @create 2020-04-01 20:51
 **/
@RestController
@CrossOrigin
public class BookshelfController {
    @Autowired
    private BookshelfService bookshelfService;

    @GetMapping("bookshelfs")
    public PageInfo<Bookshelf> page(int pageNum, int pageSize){
        return bookshelfService.page(pageNum,pageSize);
    }
    @PostMapping("/bookshelf")
    public BaseResult addBookshelf(@Valid @RequestBody Bookshelf bookshelf, BindingResult bindingResult){
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
        return bookshelfService.addBookshelf(bookshelf);
    }

    @PutMapping("/bookshelf")
    public BaseResult updateBookshelf(@Valid @RequestBody Bookshelf bookshelf, BindingResult bindingResult){
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
        return bookshelfService.updateBookshelf(bookshelf);
    }

    @DeleteMapping("/bookshelf")
    public BaseResult deleteBookshelf(String ids){
        return bookshelfService.deleteBookshelf(ids.split(","));
    }

    @GetMapping("bookshelfs/places")
    public List<String> getAllPlaces(){
        return bookshelfService.getAllPlace();
    }
    @GetMapping("bookshelfs/condi")
    public List<Bookshelf> getByPlace(String place){
        return bookshelfService.getByPlace(place);
    }

    @GetMapping("/bookshelf/count")
    public List<CategoryCountDTO> getBookshelfCount(){
        return bookshelfService.getBookshelfCount();
    }
    @GetMapping("/bookshelf/callNo")
    public Bookshelf getByBookCallNo(String callNo){
        return bookshelfService.getByBookCallNo(callNo);
    }

    @GetMapping("/bookshelf/dist")
    public List<BookshelfDistDTO> getDist(){
        return bookshelfService.getDist();
    }

    @GetMapping("/bookshelf/cat")
    public List<CategoryBookshelfDTO> getCategoryByCategory(){
        return bookshelfService.getBookshelfByCategory();
    }
}