package com.cc.service;

import com.cc.domain.R;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 16:06
 */
public interface LoginService {
    R<Map<String, Object>> login(String code);

    void register(String openId);
}
