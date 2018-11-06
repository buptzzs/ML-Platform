package com.example.admin.controller;

import com.example.admin.entity.User;
import com.example.admin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String addNewUser(@RequestParam String username, @RequestParam String password,@RequestParam String role){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    


}