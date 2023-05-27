package com.hivey.userservice.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {

    /**
     * Prometheus 연동 시 actuator가 endpoint에 접근할 수 있도록 Spring Security가 적용되지 않도록 해준다.
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers(
                "/actuator/**",
                "/actuator/prometheus",
                "/**");
    }
}
