package com.nftfont.config.security;


import com.nftfont.config.properties.CorsProperties;
import com.nftfont.common.jwt.JwtAuthenticationEntryPoint;
import com.nftfont.common.jwt.JwtAuthenticationFilter;
import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.module.user.application.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CorsProperties corsProperties;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private static final String[] GET_PERMITTED_URLS = {
            "/auth/refresh","/health","/users/**","/fonts","/font/**"
    };

    private static final String[] POST_PERMITTED_URLS = {
            "/signIn"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .userDetailsService(customUserDetailsService)
                .authorizeRequests()
                    .antMatchers("/api-docs/**","/swagger-ui/**").permitAll()
                    .antMatchers("/hello/api").permitAll()
                    .antMatchers(HttpMethod.GET,GET_PERMITTED_URLS).permitAll()
                    .antMatchers(HttpMethod.POST,POST_PERMITTED_URLS).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationFilter JwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("*"));
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
