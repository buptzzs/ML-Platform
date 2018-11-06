package com.example.admin.jwt;

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
 * @Date: 2018/11/1
 */

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtil implements Serializable {

    private String secret;

    private Long expiration;

    private String header;

    /**
     * 从数据声明中生成令牌
     * @param claim 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims){
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        String token = Jwts.builder()
                            .setClaims(claims)
                            .setExpiration(expirationDate)
                            .signWith(SignatureAlgorithm.HS512, secret)
                            .compact();
        return token;                 
    }

    /**
     * 从令牌中获取数据声明
     * 
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return token
     */
    public String generateToken(UserDetails UserDetails){
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", UserDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     * @param token
     * @return username
     */
    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     * @param token
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     * @param token
     * @return 新令牌
     */
    public String refreshToken(String token){
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && (!isTokenExpired(token));
    }

}