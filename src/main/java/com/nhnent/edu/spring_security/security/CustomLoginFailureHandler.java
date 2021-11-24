package com.nhnent.edu.spring_security.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    // TODO : #6 실습 - RedirectStrategy interface의 구현체를 이용해서 redirectStrategy 객체를 생성하세요.
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();

        boolean isInvalidUsername = (exception instanceof UsernameNotFoundException);
        boolean isInvalidPassword = (exception instanceof BadCredentialsException);

        session.setAttribute("invalidUsername", isInvalidUsername);
        session.setAttribute("invalidPassword", isInvalidPassword);

        redirectStrategy.sendRedirect(request, response, "/login/form?error");
    }

}
