package com.lcwd.user.service.config.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;


// we are creating feign cleint interceptor which will stop any request via feign client to other microservice,
// add bearer token in request header and then pass the request to other microservice
// to create interceptor we have to implement RequestInterceptor where we have to override apply() to get token details.
// here we need to externally create a bean for OAuth2AuthorizedClientManager which is responsible to fetch token

// this interceptor will automatically intercept any request made by feign client then add request header and then pass it to other microservice
@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    // we have to create bean for OAuth2AuthorizedClientManager in some config class to get client token details
    // OAuth2AuthorizedClientManager - Attempt to authorize or re-authorize (if required) the client identified by the provided clientRegistrationId.
    private OAuth2AuthorizedClientManager manager;      // this will be responsible for managing all authorized client and also for getting tokens
    @Override
    public void apply(RequestTemplate template) {
        String token = manager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("my-internal-client") // this we have defined in yml file
                .principal("internal")  // we can now use internal name for our client
                .build())           // build the object
                .getAccessToken()   // get the access toke
                .getTokenValue();

        template.header("Authorization", "Bearer "+token);  // add token in request header
    }
}
