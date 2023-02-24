package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.constants.HttpStatus;
import com.cc.constants.RedisCacheKeys;
import com.cc.domain.R;
import com.cc.domain.entity.LoginUser;
import com.cc.domain.entity.User;
import com.cc.domain.vo.UserInfoVo;
import com.cc.enums.RedisCacheExpireTime;
import com.cc.service.LoginService;
import com.cc.service.UserService;
import com.cc.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 16:06
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private WxLogin wxLogin;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public R<Map<String, Object>> login(String code) {
        //得到openID
        String openId = null;
        try {
            openId = wxLogin.getOpenId(code);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return R.fail(HttpStatus.ERROR,"服务器请求异常");
        }
        //查询数据库是否存在openID
        boolean exist = true;
        //先查缓存再查数据库
        LoginUser loginUser = redisCache.getCacheObject(RedisCacheKeys.USERINFO_KEY + openId);
        if (StringUtils.isNull(loginUser)) {
            loginUser = new LoginUser();
        }
        if (StringUtils.isNull(loginUser.getUser())) {
            LambdaQueryWrapper<User> uqw = new LambdaQueryWrapper<>();
            uqw.eq(User::getOpenid,openId);
            User user = userService.getOne(uqw);
            if (StringUtils.isNull(user)) {
                exist = false;
            } else {
                loginUser.setUser(user);
                redisCache.setCacheObject(RedisCacheKeys.USERINFO_KEY+openId, loginUser);
            }
        }
        //不存在就注册，再登录
        if(!exist) {
            register(openId);
        }
        //存在就登录
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(openId, "null");
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (StringUtils.isNull(authenticate)) {
            throw new UsernameNotFoundException("用户名不出在！");
        }

        //封装
        LoginUser user = (LoginUser) authenticate.getPrincipal();

        //生成token
        String token = tokenService.createToken(user);
        UserInfoVo userInfo = BeanUtils.copyBean(loginUser.getUser(),UserInfoVo.class);

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("token",token);
        res.put("userInfo",userInfo);
        return R.ok(res);
    }

    @Override
    public void register(String openId) {
        //
        User user = new User();
        user.setOpenid(openId);
        userService.save(user);
    }
}
