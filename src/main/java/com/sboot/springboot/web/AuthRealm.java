package com.sboot.springboot.web;

import com.sboot.springboot.dao.UserMapper;
import com.sboot.springboot.domin.User;
import com.sboot.springboot.service.IUserService;
import com.sboot.springboot.util.PropertiesUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证开始");
        String username = token.getPrincipal().toString();
        User user = userMapper.selectByUsername(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), getName());//放入shiro.调用CredentialsMatcher检验密码
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        log.info("授权开始");
        User user = (User) principal.fromRealm(getName()).iterator().next();//获取session中的用户
        List<String> permissions = new ArrayList<>();
        Integer role = user.getRole();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (role != null && role > 0) {
            info.addRole("admin");
            permissions.add("admin");
        }
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }

}