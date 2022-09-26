package com.example.demo.service;

import com.example.demo.entity.TokenPair;
import com.example.demo.entity.User;
import com.example.demo.filter.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtUtils tokenProvider;
    private final AuthenticationManager authenticationManager;

    private final  UserService userService;

    public TokenPair login(String username, String password) throws Exception {
        User user = userService.auth(username,password);
        if (Objects.isNull(user)) {
            throw new Exception("Unauthorized basic auth");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateJwtToken(authentication);
        return new TokenPair(jwt);
    }
}
