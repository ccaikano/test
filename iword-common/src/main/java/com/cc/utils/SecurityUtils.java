package com.cc.utils;

import com.cc.domain.entity.LoginUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

//    public static Boolean isAdmin(){
//        IN id = getLoginUser().getUser().getId();
//        return id != null && 1L == id;
//    }

    public static Integer getUserId() {
        return getLoginUser().getUser().getId();
    }

    public static String getOpenId() {
        return getLoginUser().getUser().getOpenid();
    }
}