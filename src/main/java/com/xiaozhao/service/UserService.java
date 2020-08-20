package com.xiaozhao.service;


import com.xiaozhao.pojo.User;

/**
 * @author 赵婷
 */
public interface UserService {

    /**
     * 登录方法，判断输入的用户名密码是否合理
     * @param userName
     * @param passWord
     * @return
     */
    boolean login(String userName, String passWord);

    /**
     * 通过用户名获得用户信息
     * @param userName
     * @return
     */
    User getUserInfo(String userName);

    /**
     * 通过用户id查询用户信息
     * @param userId
     * @return
     */
    User queryById(Long userId);
}
