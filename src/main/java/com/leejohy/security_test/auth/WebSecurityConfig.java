package com.leejohy.security_test.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/private").hasRole("USER")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated() // 모든 요청에 대해 인증 요구
                .and()
                .httpBasic(); // 이렇게 하면 팝업 뜸(이전에는 기본 창)
    }
}
