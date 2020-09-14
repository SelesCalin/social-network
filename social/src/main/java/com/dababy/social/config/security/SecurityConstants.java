package com.dababy.social.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityConstants {

    private String tokenPrefix;
    private String secretKey;
    private Long tokenExpirationAfterSeconds;

    public SecurityConstants() {
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getTokenExpirationAfterSeconds() {
        return tokenExpirationAfterSeconds;
    }

    public void setTokenExpirationAfterSeconds(Long tokenExpirationAfterSeconds) {
        this.tokenExpirationAfterSeconds = tokenExpirationAfterSeconds;
    }

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
