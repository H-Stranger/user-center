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
     *
     * @param userAccount   用户账号
     * @param password      用户密码
     * @param checkPassword 用户校验码
     * @param planetCode    星球编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String password, String checkPassword, String planetCode);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param password  用户密码
     * @return 用户实体
     */
    User doLogin(String userAccount, String password, HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param originUser 原来用户信息
     * @return safetyUser 脱敏后信息
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销功能
     *
     * @param request
     * @return 注销成功标记
     */
    int userLogout(HttpServletRequest request);
}
