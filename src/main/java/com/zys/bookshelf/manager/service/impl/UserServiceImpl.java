package com.zys.bookshelf.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.PanelDTO;
import com.zys.bookshelf.manager.dto.UserDTO;
import com.zys.bookshelf.manager.entity.User;
import com.zys.bookshelf.manager.mapper.UserMapper;
import com.zys.bookshelf.manager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-03-26 18:23
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResult login(String code, String password) {
        User user = userMapper.getByCode(code);
        if (user == null){
            return BaseResult.fail("登录失败，账户不存在！");
        }
        //加密后比对
        String passwordAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if (passwordAsHex.equals(user.getPassword())){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO,"role");
            userDTO.setRole(user.getRole().getValue());
            return BaseResult.success("登录成功！",userDTO);
        }
        return BaseResult.fail("登录失败，密码错误！");
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAllUsers();
    }

    @Override
    public PageInfo<User> page(int pageNum, int pageSize, User user) {
        //封装分页参数
        if (user == null){
            user = new User();
        }
        //开始分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByCondition(user));

        return pageInfo;
    }

    @Override
    public List<User> selectByCondition(User user) {
        return userMapper.selectByCondition(user);
    }

    @Override
    public BaseResult addUser(User user){
        try{
            //密码加密
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(md5DigestAsHex);
            int insert = userMapper.insertUser(user);
            if (insert != 1){
                return BaseResult.fail("添加用户失败！");
            }
            return BaseResult.success("添加用户成功！");
        }catch (DuplicateKeyException e){
            return BaseResult.fail("添加用户失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("添加用户失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateUser(User user){
        try {
            int update = userMapper.updateUser(user);
            if (update != 1){
                return BaseResult.fail("修改用户信息失败！");
            }
            return BaseResult.success("修改用户信息成功");
        }catch (DuplicateKeyException e){
            return BaseResult.fail("更新用户信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("更新用户信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult updateBaseUser(UserDTO user){
        try {
            int update = userMapper.updateBaseUser(user);
            if (update != 1){
                return BaseResult.fail("修改个人信息失败！");
            }
            return BaseResult.success("修改个人信息成功");
        } catch (DuplicateKeyException e){
            return BaseResult.fail("更新个人信息失败！与现有数据冲突！");
        } catch(Exception e){
            return BaseResult.fail("更新个人信息失败！请检查数据重新提交！");
        }
    }

    @Override
    public BaseResult deleteUser(String[] ids){
        try {
            int count = 0;
            for (String id : ids) {
                int i = userMapper.deleteByPrimaryKey(id);
                count += i;
            }
            if (count != ids.length){
                return BaseResult.fail("用户信息删除失败！");
            }
            return BaseResult.success("用户信息删除成功！");
        } catch (Exception e) {
            return BaseResult.fail("用户信息删除失败！请重试！");
        }
    }

    @Override
    public int addUsers(List<User> users){
        return userMapper.insertUsers(users);
    }

    @Override
    public User getByCode(String code) {
        return userMapper.getByCode(code);
    }

    @Override
    public BaseResult resetPwd(String id) {
        String pwd = DigestUtils.md5DigestAsHex("123".getBytes());
        int reset = userMapper.resetPwd(id, pwd);
        if (reset != 1){
            return BaseResult.fail("重置密码失败！");
        }
        return BaseResult.success("重置密码成功！");
    }

    @Override
    public PanelDTO getPanelValues() {
        return userMapper.getPanelValues();
    }
}