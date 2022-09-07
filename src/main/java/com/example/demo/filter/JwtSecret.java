package com.example.demo.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.security.authentication.jwt")
public class JwtSecret {

    private String secret;
}