package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.domain.R;
import com.cc.domain.dto.UserDto;
import com.cc.domain.entity.User;
import com.cc.utils.BeanUtils;
import com.cc.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import com.cc.mapper.UserMapper;
import com.cc.service.UserService;
/**
 * 用户(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-12 16:03:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public R<String> updateTarget(UserDto user) {

        LambdaQueryWrapper<User> uqw = new LambdaQueryWrapper<>();
        uqw.eq(User::getOpenid, SecurityUtils.getOpenId());
        User newUser = BeanUtils.copyBean(user, User.class);

        update(newUser,uqw);

        return R.ok();
    }
}

