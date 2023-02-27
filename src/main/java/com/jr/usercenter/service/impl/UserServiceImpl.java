package com.jr.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jr.usercenter.commons.ErrorCode;
import com.jr.usercenter.exception.BusinessException;
import com.jr.usercenter.model.domain.User;
import com.jr.usercenter.service.UserService;
import com.jr.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
* @author JR
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-04-02 13:11:54
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /**
     * 盐值，混肴密码
     */
    private static final String SALT="jr";

    @Resource
    UserMapper userMapper;

    @Override
    public long addUser(String username, String phone, Integer age, String gender) {
        //校验账号
        if(StringUtils.isAnyBlank(username)){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        if(username.length()<4){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户账号过短");
        }
        //账号禁止存在特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在特殊字符");
        }

        //账号禁止重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        long count = this.count(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号重复");
        }

        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "123456").getBytes(StandardCharsets.UTF_8));

        User user = new User();
        user.setUsername(username);
        user.setGender(gender);
        user.setAge(age);
        user.setPassword(encryptPassword);
        user.setPhone(phone);

        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户创建失败");
        }
        return user.getId();
    }

    @Override
    public User updateUser(String username, String phone, Integer age, String gender) {
        //校验账号是否为空
        if(StringUtils.isAnyBlank(username)){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        if(username.length()<4){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户账号过短");
        }
        //账号禁止存在特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在特殊字符");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = this.getOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"账号不存在");
        }

        if (!StringUtils.isAnyBlank(phone)){
            user.setPhone(phone);
        }

        if(age != null){
            user.setAge(age);
        }

        if (!StringUtils.isAnyBlank(gender)){
            user.setGender(gender);
        }

        boolean update = this.update(user, queryWrapper);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        user.setPassword(null);
        return user;
    }



    @Override
    public User queryUser(String username) {
        if (StringUtils.isAnyBlank(username)){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }

        if(username.length()<4){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户账号过短");
        }

        //账号禁止存在特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在特殊字符");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = this.getOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"账号不存在");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public Integer deleteUser(String username) {
        if (StringUtils.isAnyBlank(username)){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }

        if(username.length()<4){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户账号过短");
        }

        //账号禁止存在特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在特殊字符");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        int delete = userMapper.delete(queryWrapper);
        if(delete <= 0){
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户不存在或系统异常删除失败");
        }
        return delete;
    }


}




