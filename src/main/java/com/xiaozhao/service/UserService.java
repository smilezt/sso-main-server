package com.xiaozhao.service;


import com.xiaozhao.pojo.User;

/**
 * @author 赵婷
 */
public interface UserService {

    boolean login(String userName, String passWord);

    User getUserInfo(String userName);

    User queryById(Long userId);
}
