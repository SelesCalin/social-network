package com.dababy.social.service;


import com.dababy.social.domain.ApplicationUser;
import com.dababy.social.domain.UserRole;
import com.dababy.social.repository.PermissionRepository;
import com.dababy.social.repository.RoleRepository;
import com.dababy.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ApplicationUser user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        }


        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> roleSet){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role: roleSet) {
            authorities.addAll(role.getPermissions());
        }
        return authorities;
    }
}
