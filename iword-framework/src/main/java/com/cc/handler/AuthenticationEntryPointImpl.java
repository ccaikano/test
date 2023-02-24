package com.cc.handler;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.cc.constants.HttpStatus;
import com.cc.domain.R;
import com.cc.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 认证失败处理类 返回未授权
 *
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        log.error("认证失败 ",e);
        int code = HttpStatus.UNLOGINED;
        String msg = "请求访问：" + request.getRequestURI() + ",认证失败，无法访问系统资源";
        WebUtils.renderString(response, JSON.toJSONString(R.fail(code, msg)));
    }
}
