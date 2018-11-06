package com.example.admin.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException{                                        
        String authHeader = request.getHeader(jwtTokenUtil.getHeader());
        log.info("authHeader:{}", authHeader);
        if(authHeader != null && StringUtils.isNotEmpty(authHeader)){
            //根据token获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(authHeader);
            
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //通过用户名获取用户信息
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // 验证token和用户是否匹配
                if(jwtTokenUtil.validateToken(authHeader, userDetails)){
                    // 构造
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }        
            }
        }
        chain.doFilter(request, response);
    }

}