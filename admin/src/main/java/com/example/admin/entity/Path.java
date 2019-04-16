package com.example.admin.entity;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * copy from https://github.com/thousmile/spring-admin-vue
 * 
 * @Date: 2018/11/1
 */

@Data
@ConfigurationProperties(prefix = "path")
@Component
public class Path{

    private String pyPath;

    private String env;

}