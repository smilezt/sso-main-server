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

    List<String> list = new ArrayList<String>();

    @RequestMapping(value = "/preLogin",method = RequestMethod.GET)
    public String preLogin(String url, HttpServletRequest request, Model model) {
        System.out.println(url);
        HttpSession session = request.getSession(false);
        //发现认证中心没有全局的session会话
        if (session == null || session.getAttribute("token") == null) {
            model.addAttribute("url",url);
            return "login";
        } else {
            String token= (String) session.getAttribute("token");
            return "redirect:http://"+url+"?token=" + token;
        }
    }

    @RequestMapping("/login")
    public String login(String username, String password, String url, HttpServletRequest request, Model model) throws Exception {
        if(userService.login(username,password)){
            String token= null;
            User user = userService.getUserInfo(username);
            try {
                token = JwtUtil.createJwt(user.getUserName(),user.getUserId());
            } catch (UnsupportedEncodingException e) {
                throw new Exception("token creat fail");
            }
            System.out.println(token);
            request.getSession().setAttribute("token", token);
            list.add(token);
            //http://www.user.com:8081/user/wel?token=sdfafddfdfa
            return "redirect:http://"+url+"?token=" + token;
        } else {
            model.addAttribute("url",url);
            return "login";
        }
    }

    @RequestMapping("/checkToken")
    @ResponseBody
    public String checkToken(String token) {
        System.out.println(JwtUtil.verifyJwt(token));
        //不是伪造的
        if (list.contains(token)&&JwtUtil.verifyJwt(token)) {
            return "correct";
        }
        return "incorrect";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, String url){
        HttpSession session = request.getSession(false);
        list.clear();
        session.removeAttribute("token");
        return "redirect:http://"+url;
    }
}
















