package com.jr.usercenter.controller;

import com.jr.usercenter.commons.BaseResponse;
import com.jr.usercenter.commons.ErrorCode;
import com.jr.usercenter.commons.ResultUtils;
import com.jr.usercenter.model.domain.User;
import com.jr.usercenter.model.request.UserRegisterRequest;
import com.jr.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户接口
 * @author JR
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("/adduser")
    public BaseResponse<Long> addUser(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
           return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }
        String username = userRegisterRequest.getUsername();
        String gender = userRegisterRequest.getGender();
        String phone = userRegisterRequest.getPhone();
        Integer age = userRegisterRequest.getAge();

        if(StringUtils.isAnyBlank(username)){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }

        return ResultUtils.success(userService.addUser(username,phone,age,gender));
    }

    @PostMapping("/updateuser")
    public BaseResponse<User> update(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }
        String username = userRegisterRequest.getUsername();
        String gender = userRegisterRequest.getGender();
        String phone = userRegisterRequest.getPhone();
        Integer age = userRegisterRequest.getAge();
        if(StringUtils.isAnyBlank(username)){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }
        return ResultUtils.success(userService.updateUser(username,phone,age,gender));
    }


    @GetMapping("/queryuser")
    public BaseResponse<User> queryUser(String username){
        if(StringUtils.isAnyBlank(username)){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }

        return ResultUtils.success(userService.queryUser(username));
    }


    @PostMapping("/deleteuser")
    public BaseResponse<Integer> deleteUser(String username){
        if(StringUtils.isAnyBlank(username)){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"参数为空");
        }

        return ResultUtils.success(userService.deleteUser(username));
    }


}
