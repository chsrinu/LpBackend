package com.lp.spring.service;

import com.lp.spring.model.User;
import com.lp.spring.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    UserService defaultUserServices;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = defaultUserServices.readUser(s,false);
        List<GrantedAuthority> authorities = buildUserAuthorities(user.getRoles());
        return buildUserForAuthentication(user,authorities);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthorities(Set<UserRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole role:roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

}
