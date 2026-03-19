package com.hxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxd.common.ErrorCode;
import com.hxd.exception.BusinessException;
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

import static com.hxd.constant.UserConstant.USER_LOGIN_STATE;

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

    @Override
    public long userRegister(String userAccount, String password, String checkPassword, String planetCode) {
        //1.1校验三项是否为空
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword,planetCode)){
            //任何一个为空
            //todo 修改为自定义异常
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号小于4位");
        }
        if(password.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码或者校验码小于8位");
        }
        if(planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"星球编号大于5位");
        }
        //1.3账户不能包含特殊字符
        String regex = "^[a-zA-Z0-9]+$";
        boolean matches = userAccount.matches(regex);
        if(!matches){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能包含特殊字符");
        }
        //1.4密码和校验密码相同
        if(!password.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验码相同");
        }
        //注意顺序，数据库查询放最后，性能更好
        //1.2账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //构造查询条件，前为数据库字段名
        userQueryWrapper.eq("userAccount",userAccount);
//        long count = this.count(userQueryWrapper);
        long count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }
        //校验码不能重复
        userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"校验码重复");
        }
        //2加密
//        final String SALT = "hxd";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        //3填入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        //save会把数据库自动添加的id回传
        boolean res = this.save(user);
        //传入失败则id为null，函数返回的是long，所以判断是否为null的情况
        //考虑直接函数返回Long
        if(!res){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败");
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
        User safetyUser = getSafetyUser(user);
        //4记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser){
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




