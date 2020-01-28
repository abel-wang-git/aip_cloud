package com.wanghuiwen.core.config.authserver;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails, Serializable {
    private String username;
    private String password;
    private Long id;
    private Byte type;
    private List<Authority> authorities;
    private List<String> roles;
    private String nickname;
    private String avatar;

    public AuthUser() {
    }

    public AuthUser(String username,
                    String password,
                    List<Authority> authorities,
                    List<String> roles,
                    Long id,
                    byte type,
                    String nickname,
                    String avatar) {
        this.username = username;
        this.password = password;
        this.authorities=authorities;
        this.roles=roles;
        this.type=type;
        this.id=id;
        this.avatar=avatar;
        this.nickname=nickname;
    }

    @Override
    public Collection<? extends Authority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return username;
    }
}
