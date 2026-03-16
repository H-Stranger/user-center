package com.hxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxd.model.User;
import com.hxd.service.UserService;
import com.hxd.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
* @author hxd15
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2026-03-16 12:10:59
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Resource
    private UserMapper userMapper;
    private static final String SALT = "hxd";
    private static final String USER_LOGIN_STATE = "userLoginState";

    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {
        //1.1校验三项是否为空
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword)){
            //任何一个为空
            //todo 修改为自定义异常
            return -1;
        }
        if(userAccount.length() < 4){
            return -1;
        }
        if(password.length() < 8 || checkPassword.length() < 8){
            return -1;
        }
        //1.3账户不能包含特殊字符
        String regex = "^[a-zA-Z0-9]+$";
        boolean matches = userAccount.matches(regex);
        if(!matches){
            return -1;
        }
        //1.4密码和校验密码相同
        if(!password.equals(checkPassword)){
            return -1;
        }
        //注意顺序，数据库查询放最后，性能更好
        //1.2账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //构造查询条件，前为数据库字段名
        userQueryWrapper.eq("userAccount",userAccount);
//        long count = this.count(userQueryWrapper);
        long count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            return -1;
        }
        //2加密
//        final String SALT = "hxd";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        //3填入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        //save会把数据库自动添加的id回传
        boolean res = this.save(user);
        //传入失败则id为null，函数返回的是long，所以判断是否为null的情况
        //考虑直接函数返回Long
        if(!res){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String password, HttpServletRequest request) {
        //1.1校验三项是否为空
        if(StringUtils.isAnyBlank(userAccount,password)){
            //任何一个为空
            return null;
        }
        if(userAccount.length() < 4){
            return null;
        }
        if(password.length() < 8){
            return null;
        }
        //1.3账户不能包含特殊字符
        String regex = "^[a-zA-Z0-9]+$";
        boolean matches = userAccount.matches(regex);
        if(!matches){
            return null;
        }
        //2数据库比对信息
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            log.info("user login failed,userAccount cannot match userPassword");
            return null;
        }
        //3用户信息脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        //4记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }
}




