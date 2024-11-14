package com.example.ec_oauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ec_oauth.entities.UserEntity;
import com.example.ec_oauth.feignclient.UsersFeignClient;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsersFeignClient userFeignCLient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userFeignCLient.findByEmail(username).getBody();
        if (user == null) {
            throw new UsernameNotFoundException("Email not found!");
        }

        User userDetail = new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        
        return userDetail;
    }

}
