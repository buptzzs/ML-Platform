package com.example.admin.jwt;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails{

    private static final long serialVersionUID = 1L;

    @Getter
    private String username;

    @JsonIgnore
    @Getter
    private String password;


    @Getter
    private Collection<? extends GrantedAuthority> authorities;


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired(){
        return true; 
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled(){
        return true;
    }


}