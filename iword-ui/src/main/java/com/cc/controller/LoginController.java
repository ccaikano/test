package com.cc.controller;

import com.cc.domain.R;
import com.cc.exception.ValidateException;
import com.cc.service.LoginService;
import com.cc.utils.StringUtils;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 15:30
 */
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public R<Map<String,Object>> login(@RequestBody Map<String, String> params) {
        String code = params.get("code");
        if (StringUtils.isBlank(code)) {
            throw new ValidateException("code不能为空");
        }
        return loginService.login(code);
    }
}
