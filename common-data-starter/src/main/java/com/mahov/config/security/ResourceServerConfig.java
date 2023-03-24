//package com.mahov.config.security;
//
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@AutoConfiguration
//public class ResourceServerConfig {
//
//  @Bean
//  @Primary
//  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .mvcMatcher("/api/v1/dummy/**")
//        .authorizeRequests()
//        .mvcMatchers("/api/v1/dummy/**")
//        .access("hasAuthority('SCOPE_resource.read')")
//        .and()
//        .oauth2ResourceServer()
//        .jwt();
//    return http.build();
//  }
//}
