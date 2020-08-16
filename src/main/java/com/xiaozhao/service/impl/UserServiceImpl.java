package com.xiaozhao.service.impl;

import com.xiaozhao.dao.UserDao;
import com.xiaozhao.pojo.User;
import com.xiaozhao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 赵婷
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean login(String userName, String passWord) {
        User user = userDao.query(userName);
        if (passWord.equals(user.getPassWord())) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserInfo(String userName) {
        User user = userDao.query(userName);
        if(user != null){
            return user;
        }
        return new User();
    }

    @Override
    public User queryById(Long userId) {
        User user = userDao.queryById(userId);
        if(user != null){
            return user;
        }
        return new User();
    }
}
