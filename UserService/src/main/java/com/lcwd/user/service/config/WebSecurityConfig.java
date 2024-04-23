package com.lcwd.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration          // we can define multiple beans per method
@EnableWebSecurity      // here we have used @EnableWebSecurity bcoz this is a normal web Servlet container and we are not using webflux dependency for gateway in user-service
@EnableMethodSecurity(prePostEnabled = true)      // by this we will be able to use security in methods. for ex- hasRole(), hasAuthorities()
public class WebSecurityConfig {

    // link for reference : https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
    // since we haven't used webflux in user-service so we are directly using SecurityFilterChain which is a normal servlet container
    // so we have authorizedHttpRequests and authenticated anyRequest using oauth2ResourceServer and jwt tokens
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));   // configure oauth2resource server and jwt
        return security.build();

    }

}
