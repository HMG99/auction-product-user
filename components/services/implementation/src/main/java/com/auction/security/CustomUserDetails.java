package com.auction.security;

import com.auction.entity.UserEntity;
import com.auction.exceptions.userexceptions.UserApiException;
import com.auction.exceptions.userexceptions.UserNotFoundException;
import com.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            userEntity = userRepository.getByEmail(username);
        }catch (Exception e) {
            throw new UserApiException("Problem during login process", e);
        }
        if(userEntity == null) {
            throw new UserNotFoundException("User not found with given email");
        }

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(String.valueOf(userEntity.getRole()))
                .authorities(new ArrayList<>())
                .build();
    }
}
