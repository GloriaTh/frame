package com.sboot.springboot.service;

import com.sboot.springboot.common.ServerResponse;
import com.sboot.springboot.domin.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);
}
