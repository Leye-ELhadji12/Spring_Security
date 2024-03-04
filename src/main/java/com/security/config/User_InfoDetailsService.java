package com.security.config;

import com.security.entity.User_;
import com.security.repository.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class User_InfoDetailsService implements UserDetailsService {
    @Autowired
    private User_repository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User_> user = userRepository.findByEmail(username);
        return user.map(User_InfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User doesn't exist")) ;
    }
}
