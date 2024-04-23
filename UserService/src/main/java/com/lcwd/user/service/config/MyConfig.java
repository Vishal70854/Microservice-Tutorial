package com.lcwd.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

@Configuration  // this annotation means we can declare as many beans
// as we want with @Bean annotation above methods as per our requirement
public class MyConfig {
    @Bean    // externally configuring bean for RestTemplate as we don't have default bean for it.
    @LoadBalanced // used to load balance the instances. here it will use the name of services instead of localhost:port
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    // declare the bean of OAuth2AuthorizedClientManager
    // ClientRegistrationRepository is used to store the client registration info
    // OAuth2AuthorizedClientRepository returns the client associated details provided
    @Bean
    public OAuth2AuthorizedClientManager manager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
    ){

        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder
                                            .builder()
                                            .clientCredentials().build();

        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, auth2AuthorizedClientRepository);

        // set authorized client provider
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;
    }
}
