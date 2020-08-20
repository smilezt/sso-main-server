package com.xiaozhao.interceptor;

import com.xiaozhao.util.HttpUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author 赵婷
 */

public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token=null;
        HttpSession session = request.getSession(false);
        //判断是否已经有登录过的token
        if(session != null && session.getAttribute("token") != null){
            token = (String)session.getAttribute("token");
        }else{
            token = request.getParameter("token");
        }
        System.out.println(token);
        //验证token的有效性
        if (token != null) {
            String reqUrl = "http://www.xiaozhao.com:8090/checkToken";
            String content = "token=" + token;
            String result = HttpUtil.sendReq(reqUrl, content);
            if ("correct".equals(result)) {
                return true;
            }
        }
        //既没有session会话信息，又没有其他系统已经登录过的token信息的，sso.com进行认证登录
        response.sendRedirect("http://www.xiaozhao.com:8090/preLogin?url=www.xiaozhao.com:8090/xiaozhao/wel");
        return false;
    }
}
