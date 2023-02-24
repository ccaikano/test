package com.cc.controller;

import com.cc.domain.R;
import com.cc.domain.dto.UserDto;
import com.cc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/21 11:13
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @PutMapping("/target")
    public R<String> updateTarget(@Validated(UserDto.Target.class) @RequestBody UserDto user) {

        return userService.updateTarget(user);
    }
}
