package com.example.admin.service.impl;

import com.example.admin.entity.User;
import com.example.admin.jwt.JwtTokenUtil;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User findUserByUsername(String username) throws Exception{
        if(StringUtils.isEmpty(username)){
            throw new Exception("用户名不存在");
        }

        User user = userRepository.findByUsername(username);
        if(user == null || StringUtils.isEmpty(user.getUsername())){
            throw new Exception("用户名不存在");
        }
        log.info("user {} logging....", user);
        return user;

    }

    @Override
    public String login(String username, String password){
        UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(uToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String refreshToken(String oldToken){
        if(! jwtTokenUtil.isTokenExpired(oldToken)){
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return "error";
    }

    public User register(User user){
        String username = user.getUsername();
        if(userRepository.findByUsername(username) != null){
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        userRepository.save(user);
        return user;
    }

}