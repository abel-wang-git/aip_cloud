package com.wanghuiwen.auth.config.auth;

import com.wanghuiwen.auth.model.Power;
import com.wanghuiwen.auth.model.Role;
import com.wanghuiwen.auth.model.User;
import com.wanghuiwen.auth.service.PowerService;
import com.wanghuiwen.auth.service.UserService;
import com.wanghuiwen.core.model.AuthUser;
import com.wanghuiwen.core.model.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserService userService;

    @Resource
    private PowerService powerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("======================loadUserByUsername::" + username + "=============");

        User user = userService.findBy("username", username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<Role> roles = userService.getRole(user.getId());
        List<Authority> authorities = new ArrayList<>();
        List<String> rolestr = new ArrayList<>();


        List<Power> powers = new ArrayList<>();
        for (Role r : roles) {
            rolestr.add(r.getDescription());
            authorities.add(new Authority(r.getDescription()));
            List<Power> powers1 = powerService.getByRole(r.getId());
            if (powers1 != null) {
                powers.addAll(powers1);
            }
        }

        if (powers.size() > 0) {
            for (Power p : powers) {
                authorities.add(new Authority(p.getUrl()));
            }
        }

        return new AuthUser(user.getUsername(), user.getPassword(), authorities, rolestr, user.getId(), user.getType(), user.getNickname(), user.getAvatar());
    }
}
