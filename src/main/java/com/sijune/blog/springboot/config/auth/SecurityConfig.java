package com.sijune.blog.springboot.config.auth;

import com.sijune.blog.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//스프링 시큐리티 설정
@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() //h2-console사용을 위한 설정
                .and().authorizeRequests().antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() //url별 권한 설정
                                        .antMatchers("/api/v1/**").hasRole(Role.USER.name()).anyRequest().authenticated() //Role이 USER인 사람만 접근 가능
                .and().logout().logoutSuccessUrl("/") //로그아웃 시 url
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);  //로그인 성공후 가져온 사용자 정보로 후속조치를 진행할 Service등록


    }
}
