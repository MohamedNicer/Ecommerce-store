package com.ecs.ecommercestore.Api.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf ->
                csrf.disable());
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        httpSecurity.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        httpSecurity.authorizeHttpRequests(auth ->
                auth.requestMatchers("/product","/auth/login","/auth/register").permitAll()
                .anyRequest().authenticated());
        return httpSecurity.build();
    }

}
