package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDetailRepository userDetailRepository;
    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDetailRepository.save(user);
    }

    public User auth(String username, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        User user1 = new User(null, user.getUsername(), user.getPassword());
        return user1;
    }


}
