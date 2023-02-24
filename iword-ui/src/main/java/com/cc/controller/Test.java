package com.cc.controller;

import com.cc.domain.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/11 17:38
 */
@RestController
public class Test {

    @GetMapping("/test")
    public R<String> test() {
        return R.fail(400,"test");
    }
}
