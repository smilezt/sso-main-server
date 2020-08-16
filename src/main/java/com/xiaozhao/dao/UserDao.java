package com.xiaozhao.dao;

import com.xiaozhao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 赵婷
 */
@Repository
@Mapper
public interface UserDao {

    User query(String userName);

    User queryById(Long userId);
}
