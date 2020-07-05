package com.zys.bookshelf.manager.exception;

import com.zys.bookshelf.manager.dto.BaseResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description 全局异常处理器
 * @create 2020-03-26 20:59
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionResolver {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult handleException(Exception e) {
//        if (e instanceof DuplicateKeyException){
//            return BaseResult.fail(HttpStatus.BAD_REQUEST.value(), "和已有数据冲突，请检查后重新提交");
//        }
//        if (e instanceof SQLIntegrityConstraintViolationException){
//            return BaseResult.fail(HttpStatus.BAD_REQUEST.value(), "数据不符合要求，请检查后重新提交");
//        }
//        return BaseResult.fail(HttpStatus.BAD_REQUEST.value(), "服务器出现异常，请联系管理员");
        return BaseResult.fail(e.getMessage());
    }

}