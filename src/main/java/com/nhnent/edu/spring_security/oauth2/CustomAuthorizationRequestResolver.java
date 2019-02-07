package com.nhnent.edu.spring_security.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver delegate;


    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        delegate = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                                                                 OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
    }


    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return Optional.ofNullable(delegate.resolve(request))
                       .map(this::customizeRequest)
                       .orElse(null);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return Optional.ofNullable(delegate.resolve(request, clientRegistrationId))
                       .map(this::customizeRequest)
                       .orElse(null);
    }

    private OAuth2AuthorizationRequest customizeRequest(OAuth2AuthorizationRequest request) {
        Map<String, Object> paramMap = new HashMap<>(request.getAdditionalParameters());
        paramMap.putIfAbsent("serviceProviderCode", "FRIENDS");

        return OAuth2AuthorizationRequest.from(request)
                                         .additionalParameters(paramMap)
                                         .build();
    }

}
