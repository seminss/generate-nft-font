package com.nftfont.core.configuration.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${app.auth.tokenSecret}")
    private String secret;

    @Bean
    public JwtTokenProvider jwtProvider(){
        return new JwtTokenProvider(secret);
    }
}
