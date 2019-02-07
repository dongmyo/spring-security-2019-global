package com.nhnent.edu.spring_security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                /*
                 * TODO : #5 실습 - `/notice` 로그인을 하지 않아도 접근 가능하도록 설정하세요.
                 */
                // TODO : #4 index 페이지는 로그인 없이도 접근 가능하지만, 다른 모든 페이지는 로그인해야만 접근 가능함.
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
            .requiresChannel()
                .anyRequest().requiresInsecure()
                .and()
            .formLogin()
                .and()
            .logout()
                .and()
            .csrf()
                .disable()
            /*.headers()
                .defaultsDisabled()
                .cacheControl()*/
            .sessionManagement()
                .sessionFixation()
                    .none()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN")
                .and()
            .withUser("member")
                .password("{noop}member")
                .authorities("ROLE_MEMBER")
                .and()
            .withUser("guest")
                .password("{noop}guest")
                .authorities("ROLE_GUEST");
    }

}
