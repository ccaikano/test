package com.cc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.domain.R;
import com.cc.domain.dto.UserDto;
import com.cc.domain.entity.User;


/**
 * 用户(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-12 16:03:31
 */
public interface UserService extends IService<User> {

    /**
     * 更新目标
     * @param user 参数
     * @return R
     */
    R<String> updateTarget(UserDto user);

}

