package com.xiaozhao.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 赵婷
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    Long userId;
    String userName;
    String passWord;
}
