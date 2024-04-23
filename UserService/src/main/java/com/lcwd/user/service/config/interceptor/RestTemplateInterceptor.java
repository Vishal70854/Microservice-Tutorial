package com.lcwd.user.service.config.interceptor;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private OAuth2AuthorizedClientManager manager;  // constructor dependency injection

    private Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

    // constructor
    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//         get token value with the help of OAuth2AUthorizedClientManager

        String token = manager.authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId("my-internal-client") // this we have defined in yml file
                        .principal("internal")  // we can now use internal name for our client
                        .build())           // build the object
                .getAccessToken()   // get the access toke
                .getTokenValue();

        // get logs details for debugging only
        logger.info("Rest template interceptor token: {}",token);

        request.getHeaders().add("Authorization", "Bearer "+token);

        // return ClientHttpResponse with the help of ClientHttpRequestExecution object provided
        return execution.execute(request, body);
    }
}
