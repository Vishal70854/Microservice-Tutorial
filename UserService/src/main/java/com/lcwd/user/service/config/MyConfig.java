package com.lcwd.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // this annotation means we can declare as many beans
// as we want with @Bean annotation above methods as per our requirement
public class MyConfig {
    @Bean    // externally configuring bean for RestTemplate as we don't have default bean for it.
    @LoadBalanced // used to load balance the instances. here it will use the name of services instead of localhost:port
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
