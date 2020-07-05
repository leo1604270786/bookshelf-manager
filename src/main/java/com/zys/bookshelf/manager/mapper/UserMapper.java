package com.zys.bookshelf.manager.mapper;

import com.zys.bookshelf.manager.dto.PanelDTO;
import com.zys.bookshelf.manager.dto.UserDTO;
import com.zys.bookshelf.manager.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends MyMapper<User> {
    List<User> selectByCondition(@Param("user") User user);
    int insertUser(@Param("user") User user);
    int updateUser(@Param("user") User user);
    int updateBaseUser(@Param("user") UserDTO user);
    List<User> selectAllUsers();
    int insertUsers(@Param("users") List<User> users);
    User getByCode(@Param("code") String code);
    int resetPwd(@Param("id") String id, @Param("password") String password);
    //获取面板数据
    PanelDTO getPanelValues();
}