package com.zys.bookshelf.manager.controller;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.common.PoiUtils;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.PanelDTO;
import com.zys.bookshelf.manager.dto.UserDTO;
import com.zys.bookshelf.manager.entity.Dictionary;
import com.zys.bookshelf.manager.entity.User;
import com.zys.bookshelf.manager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description User控制器
 * @create 2020-03-26 20:57
 **/
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public PageInfo<User> page(int pageNum, int pageSize,@RequestParam(required = false) String name ,@RequestParam(required = false) String role
            ,@RequestParam(required = false) String gender, @RequestParam(required = false) String code){
        pageNum = pageNum < 0 ? 1 : pageNum;
        pageSize = pageSize < 0 ? 10 : pageSize;
        User user = new User();
        user.setCode(code);
        user.setName(name);
        user.setGender(gender);
        if (role != null && !"".equals(role)){
            Dictionary dictionary = new Dictionary();
            dictionary.setId(Integer.parseInt(role));
            user.setRole(dictionary);
        }
        return userService.page(pageNum, pageSize, user);
    }

    @PostMapping("/user")
    public BaseResult addUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if (user != null){
            user.setRegistryDate(new Date());
        }
        //数据校验
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
        return userService.addUser(user);
    }

    @PutMapping("/user")
    public BaseResult updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        //数据校验
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
        return userService.updateUser(user);
    }
    @PutMapping("/user/base")
    public BaseResult updateBaseUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        //数据校验
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
        return userService.updateBaseUser(userDTO);
    }
    @DeleteMapping("/user")
    public BaseResult deleteUser(String ids){
        return userService.deleteUser(ids.split(","));
    }
    @GetMapping("/user/pwd")
    public BaseResult resetPwd(String id){
        return userService.resetPwd(id);
    }
    @GetMapping("/user/export")
    public ResponseEntity<byte[]> exportUser(){
        return PoiUtils.exportUser2Excel(userService.selectAll());
    }

    @PostMapping("/user/import")
    public BaseResult importUser(MultipartFile file) {
        List<User> users = PoiUtils.importUser2List(file);
        if (userService.addUsers(users) == users.size()){
            return BaseResult.success("导入成功！");
        }
        return BaseResult.fail("导入失败！");
    }
    @GetMapping("/panel")
    public PanelDTO getPanelValues(){
        return userService.getPanelValues();
    }
}