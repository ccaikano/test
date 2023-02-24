package com.cc.filter;

import com.alibaba.fastjson.JSON;
import com.cc.constants.Constants;
import com.cc.constants.HttpStatus;
import com.cc.domain.R;
import com.cc.domain.entity.LoginUser;
import com.cc.utils.SecurityUtils;
import com.cc.utils.StringUtils;
import com.cc.utils.TokenService;
import com.cc.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author CC
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{


    @Autowired
    TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {

        //放行 /login
        if (request.getRequestURI().contains(Constants.LOGIN_URL)) {
            chain.doFilter(request, response);
            return;
        }

        LoginUser loginUser = tokenService.getLoginUser(request);

        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //账号封禁
            if (Constants.IS_BAN.equals(loginUser.getUser().getStatus())) {
                WebUtils.renderString(response, JSON.toJSONString(R.fail(HttpStatus.BAN_USER,"账号已被封禁")));
                return;
            }
        }


        chain.doFilter(request, response);
    }
}
