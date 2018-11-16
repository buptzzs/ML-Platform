package com.example.admin.jwt;

import com.example.admin.entity.User;
import com.example.admin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        
        if (user == null){
            String msg = String.format("'%s'. 此用户不存在", username);
            log.debug(msg);
            throw new UsernameNotFoundException(msg);
        }
        else{
            //目前每个人只有一种角色，要么管理员，要么普通用户
            List<SimpleGrantedAuthority> collect = new ArrayList<SimpleGrantedAuthority>();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
            collect.add(authority);
            log.debug("用户权限:{}", authority.getAuthority());;
            return new JwtUser(user.getUsername(),user.getPassword(), collect);
        }
    }
} 