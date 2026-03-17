package com.hxd.service;
import java.util.Date;

import com.hxd.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 * @author hxd
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("1");
        user.setUserAccount("5446");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("875456");
        user.setPhone("");
        user.setEmail("");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        boolean res = userService.save(user);
        System.out.println(user.getId());
        assertTrue(res);
    }

    @Test
    void userRegister() {
//        //密码为空
        String userAccount = "yupi";
        String password = "";
        String checkPassword = "12345678";
        String planetCode = "10";
        long result = userService.userRegister(userAccount, password, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//        //用户名小于4位
//        userAccount = "hxd";
//        password = "12345678";
//        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//        //密码小于8位
//        userAccount = "yupi";
//        password = "123456";
//        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//        //特殊字符
//        userAccount = "##@@$$fda";
//        password = "12345678";
//        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//        //密码和校验码相同
//        userAccount = "yupi";
//        password = "1234567";
//        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//        //插入数据
//        userAccount = "yupi";
//        password = "12345678";
//        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
//        Assertions.assertTrue(result >0);
        //插入数据
        userAccount = "yupi1";
        password = "12345678";
        result = userService.userRegister(userAccount,password,checkPassword,planetCode);
        Assertions.assertTrue(result >0);
    }


}