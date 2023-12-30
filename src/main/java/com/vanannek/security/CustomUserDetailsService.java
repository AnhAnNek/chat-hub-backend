package com.vanannek.security;

import com.vanannek.entity.User;
import com.vanannek.exception.UserNotFoundException;
import com.vanannek.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepos userRepos;

    private static final List<String> defaultRoles = List.of("USER");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepos
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassHash(), mapRolesToAuthorities(defaultRoles));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
