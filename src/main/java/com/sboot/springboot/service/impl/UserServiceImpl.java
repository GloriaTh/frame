package com.sboot.springboot.service.impl;

import com.sboot.springboot.common.ServerResponse;
import com.sboot.springboot.dao.UserMapper;
import com.sboot.springboot.domin.User;
import com.sboot.springboot.service.IUserService;
import com.sboot.springboot.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectByUsernameAPassword(username, md5Password);

        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        int count = userMapper.checkUsername(user.getUsername());
        if (count == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        count = userMapper.checkEmail(user.getEmail());
        if (count == 0) {
            return ServerResponse.createByErrorMessage("电子邮箱不存在");
        }

        count = userMapper.insert(user);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("未知原因,注册失败");
        }

        return ServerResponse.createBySuccess("注册成功");
    }
}
