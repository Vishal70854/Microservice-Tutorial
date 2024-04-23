package com.lcwd.user.service.config;

import com.lcwd.user.service.config.interceptor.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration  // this annotation means we can declare as many beans
// as we want with @Bean annotation above methods as per our requirement
public class MyConfig {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean    // externally configuring bean for RestTemplate as we don't have default bean for it.
    @LoadBalanced // used to load balance the instances. here it will use the name of services instead of localhost:port
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate(); // object of rest template

        // here we can manually provide configuration for rest template interceptor since we have created RestTemplate bean externally
        // create List<ClientHttpRequestInterceptor> and
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RestTemplateInterceptor(manager(
                clientRegistrationRepository, oAuth2AuthorizedClientRepository
        )));

        restTemplate.setInterceptors(interceptors); // add list of interceptors

        return restTemplate;
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
