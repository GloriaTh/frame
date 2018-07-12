package com.sboot.springboot.controller;

import com.sboot.springboot.common.Const;
import com.sboot.springboot.common.ServerResponse;
import com.sboot.springboot.common.ValidateFiled;
import com.sboot.springboot.common.ValidateGroup;
import com.sboot.springboot.domin.Test;
import com.sboot.springboot.domin.User;
import com.sboot.springboot.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService iUserService;
    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    @ResponseBody
    @ValidateGroup(
            fileds = {@ValidateFiled(index = 0, notNull = true),
                    @ValidateFiled(index = 1, notNull = true)}
    )
    public ServerResponse<User> login(@PathVariable("username") String username,
                                      @PathVariable("password") String password,
                                      HttpSession session) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
            return serverResponse;
        }
        return serverResponse;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(@RequestBody(required = false) Test user) {
        System.out.println(user);
        return null;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> loginOut(HttpSession session) {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> test(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.isPermitted("admin");
        subject.hasRole("admin");
        subject.checkRole("admin");

        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public ServerResponse<String> upload(MultipartFile file, HttpServletRequest request) {
        try {
            byte[] bytes = file.getBytes();
            File test = new File(path);
            if (!test.exists()) {
                test.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(new File(test.getAbsolutePath() + "/" + file.getOriginalFilename()));
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public void get(String get, HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = new File(path + "/" + get);
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream os = response.getOutputStream();
            int by = 0;
            while ((by = fis.read()) != -1) {
                os.write(by);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
