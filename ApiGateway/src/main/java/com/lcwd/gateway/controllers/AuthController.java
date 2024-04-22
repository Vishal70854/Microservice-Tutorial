package com.lcwd.gateway.controllers;

import com.lcwd.gateway.models.AuthResponse.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

//    OAuth2AuthorizedClient will give the client details for ex: access-token value, client registration details
//    OidcUser will give User registration details for ex: email, profile etc
    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model
    ){

        // we will get some objects from OKTA like client details(access_token, refresh_token, expiresAt)
        // and user details like (email, List<String> authorities etc.) and from there we will return reponse of type AuthResponse

        logger.info("User email id: {}", user.getEmail());

        // create object of Authresponse
        AuthResponse authResponse = new AuthResponse();

        // setting email to authreponse
        authResponse.setUserId(user.getEmail());

        // setting token to authresponse
        authResponse.setAccessToken(client.getAccessToken().getTokenValue());

        //setting refreshTokenValue to authresponse
        authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());

        // setting expiresAt to authresponse
        authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        // using stream we got each authority and we want List<String> so we have converted every authority to String and returned
        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());

        // setting List<authorities> to authresponse
        authResponse.setAuthorities(authorities);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
