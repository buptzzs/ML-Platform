package com.example.admin.controller;

import com.example.admin.entity.Result;
import com.example.admin.entity.User;
import com.example.admin.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Api(value="/auth", tags="用户鉴权接口")
@Slf4j
@RestController
public class AuthController{

    @Autowired
    private UserService userService;

    @Value("${resource.local_files_path}")
    private String base_path;

    @ApiOperation(value="用户登陆", notes="用户登录接口")
    @PostMapping(value = "${jwt.route.login}")
    public Result<String> login(@RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");        
        log.info("Login...username:"+username+",password:"+password);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return Result.error401("用户或者密码为空", null);
        }

        return Result.success("用户登录成功：test", userService.login(username, password));
    }

    //这里的设置可能需要改变，RequestParam可能需要改成RequestBody
    @PostMapping(value = "${jwt.route.refresh}")
    public Result<String> refresh(@RequestHeader("${jwt.header}") String token) {
        return Result.success("刷新token成功!", userService.refreshToken(token));
    }

    @PostMapping("/auth/test")
    public Result<String> test(){
        return Result.success(base_path);
    }

    @PostMapping(value = "${jwt.route.register}")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    //后续特定的用户信息
    @ApiOperation(value="获取登陆后的当前用户信息")
    @GetMapping(value = "${jwt.route.info}")
    public Result<User> userInfo() throws Exception{
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        User user = userService.findUserByUsername(userDetails.getUsername());
        log.info("get user info:{}", user);
        return Result.success(user);
    }

    @ApiOperation(value="用户登出")
    @PostMapping(value = "${jwt.route.logout}")
    public Result<String> logout() {
        log.info("log out");
        return Result.success("logout success");
    }
}