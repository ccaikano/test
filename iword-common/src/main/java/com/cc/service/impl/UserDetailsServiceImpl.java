package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.constants.RedisCacheKeys;
import com.cc.domain.entity.LoginUser;
import com.cc.domain.entity.User;
import com.cc.enums.RedisCacheExpireTime;
import com.cc.service.UserService;
import com.cc.utils.RedisCache;
import com.cc.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录验证
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
        //查询
        //查缓存
         LoginUser loginUser = redisCache.getCacheObject(RedisCacheKeys.USERINFO_KEY + openId);
         if (StringUtils.isNull(loginUser)) {
             loginUser = new LoginUser();
         }
         if (StringUtils.isNull(loginUser.getUser())) {
            //查数据库
            LambdaQueryWrapper<User> uqw = new LambdaQueryWrapper<>();
            uqw.eq(User::getOpenid,openId);
            User user = userService.getOne(uqw);
            if (StringUtils.isNull(user)) {
                throw new UsernameNotFoundException("用户不存在！");
            } else {
                loginUser.setUser(user);
                redisCache.setCacheObject(RedisCacheKeys.USERINFO_KEY+openId,
                        loginUser);
            }
        }

        return loginUser;
    }
}