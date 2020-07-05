package com.zys.bookshelf.manager.service;

import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.PanelDTO;
import com.zys.bookshelf.manager.dto.UserDTO;
import com.zys.bookshelf.manager.entity.User;

import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-26 18:22
 **/
public interface UserService {

    BaseResult login(String code, String password);

    List<User> selectAll();

    PageInfo<User> page(int pageNum, int pageSize, User user);

    List<User> selectByCondition(User user);

    BaseResult addUser(User user);

    BaseResult updateUser(User user);

    BaseResult updateBaseUser(UserDTO user);

    BaseResult deleteUser(String[] ids);

    int addUsers(List<User> users);

    User getByCode(String code);

    BaseResult resetPwd(String id);

    PanelDTO getPanelValues();
}
