package com.hxd.service;

import com.hxd.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author hxd15
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2026-03-16 12:10:59
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 用户账号
     * @param password 用户密码
     * @param checkPassword 用户校验码
     * @return 新用户id
     */
    long userRegister(String userAccount,String password,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param password  用户密码
     * @return 用户实体
     */
    User doLogin(String userAccount, String password, HttpServletRequest request);
}
