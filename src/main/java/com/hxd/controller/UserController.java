package com.hxd.controller;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxd.common.BaseResponse;
import com.hxd.common.ErrorCode;
import com.hxd.common.Result;
import com.hxd.common.ResultUtils;
import com.hxd.exception.BusinessException;
import com.hxd.model.User;
import com.hxd.model.request.UserLoginRequest;
import com.hxd.model.request.UserRegisterRequest;
import com.hxd.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hxd.constant.UserConstant.ADMIN_ROLE;
import static com.hxd.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    //将零散的参数封装为请求类
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null) {
//            return Result.error("未发送用户注册请求");
//            return null;
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //查看内容是否为空
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword,planetCode)){
//            return Result.error("注册请求参数为空");
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long id = userService.userRegister(userAccount, password, checkPassword, planetCode);
        return ResultUtils.success(id);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if(userLoginRequest == null){
//            return Result.error("未发送登录请求");
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if(StringUtils.isAnyBlank(userAccount,password)){
//            return Result.error("登录请求参数为空");
            return null;
        }
        User user = userService.doLogin(userAccount, password, httpServletRequest);
        return ResultUtils.success(user);
    }
    @PostMapping("/logout")
    public Result userLogout(HttpServletRequest request){
        if(request == null) return Result.error("注销请求为空");
        int res = userService.userLogout(request);
        return Result.success(res);
    }
    @GetMapping("/current")
    public Result getCurrentUser(HttpServletRequest request){
        //获取当前用户session
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null){
            return Result.error("当前未登录");
        }
        //todo 校验用户是否合法
        //数据查找用户当前信息（session信息可能和数据库不一致）
        User user = userService.getById(currentUser.getId());
        User safetyUser = userService.getSafetyUser(user);
        return Result.success(safetyUser);
    }
    @GetMapping("/search")
    public Result searchUsers(String username,HttpServletRequest request){
        if(!isAdmin(request)){
            return Result.error("用户权限不足");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //如果username为null，则查询不出来，空字符串可以查询出来
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        //信息脱敏
        List<User> safetyUserList = userList.stream().map(user -> {
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return Result.success(safetyUserList);
    }

    @PostMapping("/delete")
    public Result delete(long id,HttpServletRequest request){
        if(!isAdmin(request)) return Result.error("用户权限不足");
        if(id <= 0) return Result.error("id错误");
        //自动转变为逻辑删除
        userService.removeById(id);
        return Result.success();
    }


    private boolean isAdmin(HttpServletRequest request){
        //验权
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
