package com.nhnent.edu.spring_security.config;

import com.nhnent.edu.spring_security.encoder.Sha256PasswordEncoder;
import com.nhnent.edu.spring_security.oauth2.CustomAuthorizationRequestResolver;
import com.nhnent.edu.spring_security.oauth2.CustomRequestEntityConverter;
import com.nhnent.edu.spring_security.oauth2.PaycoOAuth2User;
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
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

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
                .antMatchers("/admin/**").requiresSecure()
                .antMatchers("/private-project/**").requiresSecure()
                .antMatchers("/project/**").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
            .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll()
                .and()
            .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .authorizationEndpoint()
                    .authorizationRequestResolver(customAuthorizationRequestResolver())
                    .and()
                .userInfoEndpoint()
                    .userService(oauth2UserService())
                    .and()
                .and()
//            .formLogin()
//                .loginPage("/login/form")
//                .usernameParameter("name")
//                .passwordParameter("pwd")
//                .loginProcessingUrl("/login/process")
//                .failureHandler(loginFailureHandler())
//                .and()
            .logout()
                .and()
            .headers()
                .defaultsDisabled()
                .frameOptions().sameOrigin()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/error/403")
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

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(ClientRegistration.withRegistrationId("payco")
                                                                          .clientId("3RDU4G5NI_cxk4VNvSI7")
                                                                          .clientSecret("fxVFAe2HjN98DOyrV6kyJVHD")
                                                                          .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                                                          .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                                                                          .authorizationUri("https://alpha-id.payco.com/oauth2.0/authorize")
                                                                          .tokenUri("https://alpha-id.payco.com/oauth2.0/token")
                                                                          .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                                                                          .userInfoUri("https://dev-apis.krp.toastoven.net/payco/friends/getMemberProfileByFriendsToken.json")
                                                                          .userInfoAuthenticationMethod(AuthenticationMethod.FORM)
                                                                          .build());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public CustomAuthorizationRequestResolver customAuthorizationRequestResolver() {
        return new CustomAuthorizationRequestResolver(clientRegistrationRepository());
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        // TODO : #1 DefaultOAuth2UserService 대신 CustomUserTypesOAuth2UserService 사용.
        //        PAYCO ID UserInfo 응답 결과를 지원하는 OAuth2User 확장 클래스 PaycoOAuth2User 정보를 생성자에 전달.
        Map<String, Class<? extends OAuth2User>> customUserTypes = new HashMap<>();
        customUserTypes.put("payco", PaycoOAuth2User.class);

        CustomUserTypesOAuth2UserService oauth2UserService = new CustomUserTypesOAuth2UserService(customUserTypes);
        oauth2UserService.setRequestEntityConverter(requestEntityConverter());

        return oauth2UserService;
    }

    @Bean
    public CustomRequestEntityConverter requestEntityConverter() {
        return new CustomRequestEntityConverter();
    }

}
