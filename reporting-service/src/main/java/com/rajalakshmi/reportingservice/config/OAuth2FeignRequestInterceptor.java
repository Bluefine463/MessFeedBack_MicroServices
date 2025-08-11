package com.rajalakshmi.reportingservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2FeignRequestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("feedback-service-client")
                .principal("reporting-service")
                .build();

        OAuth2AuthorizedClient client = authorizedClientManager.authorize(request);
        if (client != null && client.getAccessToken() != null) {
            template.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
        }
    }
}
