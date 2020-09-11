package com.project.covid19.config;

import com.project.covid19.constants.SecurityConstants;
import com.project.covid19.security.custom.CustomUserDetailsService;
import com.project.covid19.security.filter.JwtAuthenticationFilter;
import com.project.covid19.security.filter.JwtAuthorizationFilter;
import com.project.covid19.security.handler.CustomAccessDeniedHandler;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Log
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure");
        http.cors()
                .and()
                // csrf 요청 비활성화 및 exception 발생시 발생한 exception을 처리할 default Exception Handler 등록
                .csrf().disable()
                .exceptionHandling()
                // 요청이 거부가 되었을 때 해야 할 일을 정의한 Access DeniedHander 정의
                .accessDeniedHandler(createAccessDeniedHandler())
                .and()
                // 인증 (Authentication) 절차에 사용할 Filter 등록
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                // 권한 (Authorization) 젎차에 사용할 Filter 등록
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // 세션 관리 설정
                .sessionManagement()
                // 세션의 정책은 Stateless로 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/board").permitAll()
                .antMatchers(HttpMethod.GET, "/board/{boardNo}").permitAll()
                .antMatchers(HttpMethod.POST, "/member/register").permitAll()
                .antMatchers(HttpMethod.GET, "/member/checkid/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/member/checknick/{nickName}").permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.AUTH_LOGIN_URL).permitAll()
                .antMatchers("/craw/**").permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AccessDeniedHandler createAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
