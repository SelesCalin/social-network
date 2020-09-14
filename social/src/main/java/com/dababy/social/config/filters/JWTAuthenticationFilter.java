package com.dababy.social.config.filters;

import com.dababy.social.config.security.SecurityConstants;
import com.dababy.social.web.dto.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final SecurityConstants securityConstants;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SecurityConstants securityConstantsConfig) {
        this.authenticationManager = authenticationManager;
        this.securityConstants=securityConstantsConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
            log.debug("attemptAuthentication --> user = {}", authenticationRequest);
            Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            return auth;
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder().setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(securityConstants.getTokenExpirationAfterSeconds())))
                .signWith(Keys.hmacShaKeyFor(securityConstants.getSecretKey().getBytes()))
                .compact();

        response.addHeader(securityConstants.getAuthorizationHeader(),securityConstants.getTokenPrefix() + token);

    }
}
