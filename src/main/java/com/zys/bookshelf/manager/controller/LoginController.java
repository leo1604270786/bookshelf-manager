package com.zys.bookshelf.manager.controller;

import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description 登录控制器
 * @create 2020-03-27 18:09
 **/
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResult login(@RequestParam(name = "username") String code, @RequestParam(name = "password") String password){
        return userService.login(code, password);
    }
}