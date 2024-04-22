package com.lcwd.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


// we have used EnableWebFluxSecurity since api-gateway uses webflux and cloud starter gateway for handling request
// that's why we have used EnableWebFluxSecurity instead of WebSecurity since api-gateway uses webflux for cloud gateway


@Configuration      // this annotation means we can have multiple beans per method
@EnableWebFluxSecurity  // we have used EnableWebFluxSecurity since api-gateway uses webflux and cloud starter gateway for handling request
public class SecurityConfig {
    @Bean   // bean of security web filter chain as we have used webflux for cloud gateway... this is different from securityFilterChain
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return httpSecurity.build();
    }


}
