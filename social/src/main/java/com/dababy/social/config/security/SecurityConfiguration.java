package com.dababy.social.config.security;

import com.dababy.social.config.filters.JWTAuthenticationFilter;
import com.dababy.social.config.filters.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final SecurityConstants securityConstants;


    @Autowired
    public SecurityConfiguration(@Qualifier("userDetailsServiceImpl")UserDetailsService userDetailsService, SecurityConstants constants) {
        this.userDetailsService = userDetailsService;
        this.securityConstants= constants;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean(), securityConstants);
        authenticationFilter.setFilterProcessesUrl("/api/login");
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(securityConstants), JWTAuthenticationFilter.class)
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers( "/","/index", "/api/login", "/api/register").permitAll()
                .antMatchers("/api/home").hasAuthority("GOD_AUTHORITY")
                .anyRequest().permitAll()
        ;


    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name="encoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
