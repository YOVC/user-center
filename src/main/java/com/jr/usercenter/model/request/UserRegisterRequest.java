package com.jr.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author JR
 */
@Data
public class UserRegisterRequest implements Serializable{

    private static final long serialVersionUID = 2918436888182706710L;
     private String username;
     private String gender;
     private Integer age;
     private String phone;
}
