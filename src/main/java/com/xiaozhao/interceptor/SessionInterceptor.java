package com.xiaozhao.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 赵婷
 */

public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.sendRedirect("http://www.xiaozhao.com:8090/preLogin?url=www.xiaozhao.com:8090/xiaozhao/wel");
        return false;
    }
}
