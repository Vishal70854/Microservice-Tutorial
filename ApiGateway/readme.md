## Spring cloud gateway using webflux
https://docs.spring.io/spring-cloud-gateway/reference/index.html

## spring cloud gateway using webflux 
https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/gateway-request-predicates.html

## Spring security webflux using okta
https://docs.spring.io/spring-security/reference/reactive/configuration/webflux.html

## Spring security using okta
https://developer.okta.com/

## How OKTA works internally in Api-Gateway

    // note : inorder to use spring security using OKTA we have to create account on okta developer site and then 
    // we need to create people, groups and Application.
    // add spring security and okta dependency in pom.xml
    // after that we need to configure people to group, people to Application and group to Application
    // then we need to give OKTA configurations in application.yml file 
    // After that we will create SecurityWebFilterChain object in some config file i.e SecurityConfig class which
    // basically allows all http request exchange, as well as oauth2 client login and fetching details from
    // oauth2 resource server using jwt tokens
    // Then we will define one login endpoint in AuthController for login in the user where we will get
    // @RegisteredOAuth2AuthorizedClient("okta") for getting okta client info like (access_token, refresh_token, expiresAt etc)
    // Also @AuthenticationPrincipal i.e OpenId user for getting user details like email, profile and List<String> authorities(i.e scopes)
    // the login method in AuthController will take the above mentioned annotations as parameter and will get all the client and user details
    // in the form of AuthResponse(class) and return as ResponseEntity<AuthResponse> to the client/user
    // in the response we will get email, access_token, refresh_token, expiresAt, and list of authorities
    // with this access_token the user will be validated and it will be able to use the endpoints of api gateway which routes to other microservices