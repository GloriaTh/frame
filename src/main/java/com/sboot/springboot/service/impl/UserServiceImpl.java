package com.sboot.springboot.service.impl;

import com.sboot.springboot.common.ServerResponse;
import com.sboot.springboot.dao.UserMapper;
import com.sboot.springboot.domin.User;
import com.sboot.springboot.service.IUserService;
import com.sboot.springboot.util.MD5Util;
import com.sboot.springboot.util.RabbitSender;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RabbitSender rabbitSender;

    @Override
    public ServerResponse<String> login(String username, String password) {
        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);

        return ServerResponse.createBySuccess("登陆成功");
    }

    @Override
    public ServerResponse<String> register(User user) {
        int count = userMapper.checkUsername(user.getUsername());
        if (count != 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        count = userMapper.checkEmail(user.getEmail());
        if (count != 0) {
            return ServerResponse.createByErrorMessage("电子邮箱已存在");
        }

        user.setPassword(MD5Util.MD5Encode(user.getPassword(), user.getUsername()));
        rabbitSender.neoSend(user);

        return ServerResponse.createBySuccess("注册成功");
    }
}
