package com.nhnent.edu.spring_security.config;

import com.nhnent.edu.spring_security.encoder.Sha256PasswordEncoder;
import com.nhnent.edu.spring_security.security.CustomLoginFailureHandler;
import com.nhnent.edu.spring_security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

/*
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:beans="http://www.springframework.org/schema/beans"
             xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
                                 http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <http use-expressions="true">
        <intercept-url pattern="/admin/**"           access="hasAuthority('ROLE_ADMIN')" />
        <intercept-url pattern="/private-project/**" access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')" />
        <intercept-url pattern="/project/**"         access="isAuthenticated()" />
        <intercept-url pattern="/redirect-index"     access="isAuthenticated()" />
        <intercept-url pattern="/**"                 access="permitAll()" />

        <csrf disabled="true" />
        <form-login />
        <logout />
        <session-management session-fixation-protection="none" />
    </http>

    <authentication-manager>
        <authentication-provider>
            <user-service>
                <user name="admin"  password="{noop}admin" authorities="ROLE_ADMIN" />
                <user name="member" password="{noop}member" authorities="ROLE_MEMBER" />
                <user name="guest"  password="{noop}guest" authorities="ROLE_GUEST" />
            </user-service>
        </authentication-provider>
    </authentication-manager>

</beans:beans>
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel()
                /* TODO : #2 실습 - 관리툴/비공개 프로젝트/프로젝트 페이지는 secure로 접속되도록 설정해주세요. */
                .anyRequest().requiresInsecure()
                .and()
            .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                /* TODO : #3 실습 - 비공개 프로젝트 URL은 (`/private-project/**`) ADMIN 이나 MEMBER 권한이 있을 때 접근 가능하도록 설정해주세요. */
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login/form")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login/process")
                .failureHandler(loginFailureHandler())
                .and()
            .logout()
                .and()
            .headers()
                .defaultsDisabled()
                /* TODO : #4 실습 - Security HTTP Response header 중 `X-Frame-Options` 헤더의 값을 SAMEORIGIN으로 설정해주세요. */
                .and()
            .exceptionHandling()
                /* TODO : #11 실습 - custom 403 에러 페이지(`/error/403`)를 설정해주세요. */
                .and()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionFixation()
                    .none()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(new Sha256PasswordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;
    }

    @Bean
    public CustomLoginFailureHandler loginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

}
