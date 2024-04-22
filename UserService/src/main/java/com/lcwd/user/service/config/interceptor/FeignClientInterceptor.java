package com.lcwd.user.service.config.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    // we have to create bean for OAuth2AuthorizedClientManager in some config class
    private OAuth2AuthorizedClientManager manager;      // this will be responsible for managing all authorized client and also for getting tokens
    @Override
    public void apply(RequestTemplate template) {
        String token = manager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("my-internal-client") // this we have defined in yml file
                .principal("internal")  // we can now use internal name for our client
                .build())
                .getAccessToken()   // get the access toke
                .getTokenValue();

        template.header("Authorization", "Bearer "+token);
    }
}
