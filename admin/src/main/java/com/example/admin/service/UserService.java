package com.example.admin.service;

import com.example.admin.entity.User;

public interface UserService {
    User findUserByUsername(String username) throws Exception;

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return token
     */
    String login(String username, String password);

    

    User register(User user);
    

    /**
     * 更新密钥
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);

}