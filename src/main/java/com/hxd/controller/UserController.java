package com.hxd.controller;

import com.hxd.model.User;
import com.hxd.model.request.UserLoginRequest;
import com.hxd.model.request.UserRegisterRequest;
import com.hxd.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    //将零散的参数封装为请求类
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null) {
            return null;
        }
        //查看内容是否为空
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount, password, checkPassword);
    }
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if(userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if(StringUtils.isAnyBlank(userAccount,password)){
            return null;
        }
        return userService.doLogin(userAccount,password,httpServletRequest);
    }
}
