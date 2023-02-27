package com.jr.usercenter.service;

import com.jr.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;


import javax.servlet.http.HttpServletRequest;

/**
* @author JR
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-04-02 13:11:54
*/
public interface UserService extends IService<User> {

    /***
     * 添加角色
     * @param username 用户账号
     * @param phone 手机号
     * @param age 年龄
     * @param gender 性别
     * @return 返回用户id
     */
    long addUser(String username, String phone,Integer age,String gender);

    /**
     * 角色修改
     *
     */
    User updateUser(String username,String phone,Integer age,String gender);


    /**
     * 角色查询
     */

    User queryUser(String username);


    Integer deleteUser(String username);
}
