package com.dababy.social.config.filters;

import com.dababy.social.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final SecurityConstants securityConstants;

    public JWTAuthorizationFilter(SecurityConstants securityConstants) {

        this.securityConstants = securityConstants;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(securityConstants.getAuthorizationHeader());

        if (authorization == null || authorization.equals("") || !authorization.startsWith(securityConstants.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.replace(securityConstants.getTokenPrefix(), "");
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(securityConstants.getSecretKey().getBytes()))
                    .build().parseClaimsJws(token).getBody();

            String user = claims.getSubject();


            List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");
            Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
            authorities.forEach(a -> authoritiesSet.add(new SimpleGrantedAuthority(a.get("authority"))));

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, authoritiesSet));
            filterChain.doFilter(request, response);


        } catch (ExpiredJwtException | SignatureException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }

    }

}
