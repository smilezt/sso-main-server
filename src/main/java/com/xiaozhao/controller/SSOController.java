package com.xiaozhao.controller;

import com.xiaozhao.pojo.User;
import com.xiaozhao.service.UserService;
import com.xiaozhao.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵婷
 */
@Controller
public class SSOController {

    @Autowired
    UserService userService;

    //存储token的列表
    List<String> list = new ArrayList<String>();

    /**
     * 查看是否已经在主系统上登录
     * @param url
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/preLogin",method = RequestMethod.GET)
    public String preLogin(String url, HttpServletRequest request, Model model) {
        System.out.println(url);
        HttpSession session = request.getSession(false);
        //发现认证中心没有全局的session会话，或者token已经被移除（登出）
        if (session == null || session.getAttribute("token") == null) {
            model.addAttribute("url",url);
            return "login";
        } else {//已经登录过
            String token= (String) session.getAttribute("token");
            return "redirect:http://"+url+"?token=" + token;
        }
    }

    /**
     * 登录系统
     * @param username
     * @param password
     * @param url
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public String login(String username, String password, String url, HttpServletRequest request, Model model) throws Exception {
        //验证账户密码的正确性
        if(userService.login(username,password)){
            String token= null;
            //获得用户信息
            User user = userService.getUserInfo(username);
            try {
                //创建token
                token = JwtUtil.createJwt(user.getUserName(),user.getUserId());
            } catch (UnsupportedEncodingException e) {
                throw new Exception("token creat fail");
            }
            System.out.println(token);
            //全局seesion中设置token
            request.getSession().setAttribute("token", token);
            list.add(token);
            //http://www.user.com:8081/user/wel?token=sdfafddfdfa
            //给子系统颁发token
            return "redirect:http://"+url+"?token=" + token;
        } else {
            //账户密码不匹配，重新输入进行登录
            model.addAttribute("url",url);
            return "login";
        }
    }

    /**
     * 检验token的正确性
     * @param token
     * @return
     */
    @RequestMapping("/checkToken")
    @ResponseBody
    public String checkToken(String token) {
        System.out.println(JwtUtil.verifyJwt(token));
        //不是伪造的，存在于token列表中且是合法有效的
        if (list.contains(token)&&JwtUtil.verifyJwt(token)) {
            return "correct";
        }
        return "incorrect";
    }

    /**
     * 登出
     * @param request
     * @param url
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, String url){
        HttpSession session = request.getSession(false);
        //清空token列表
        list.clear();
        //清除session中的token
        session.removeAttribute("token");
        //重定向
        return "redirect:http://"+url;
    }
}
















