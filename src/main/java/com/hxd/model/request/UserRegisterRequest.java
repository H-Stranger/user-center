package com.hxd.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -4665957200823610123L;
    String userAccount;

    String password;

    String checkPassword;
}
