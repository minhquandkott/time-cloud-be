package com.ces.intern.apitimecloud.security;

import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.security.filter.AuthenticationFilter;
import com.ces.intern.apitimecloud.security.filter.AuthorizationFilter;
import com.ces.intern.apitimecloud.security.handler.AuthenticationFailureHandler;
import com.ces.intern.apitimecloud.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userDetailService;

    public WebSecurity(PasswordEncoder passwordEncoder,
                       UserService userDetailService){
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.POST,SecurityContact.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityContact.SIGN_IN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                //.addFilter(configAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()));

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder);
//    }

//    protected  AuthenticationFilter configAuthenticationFilter() throws Exception {
//        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
//        authenticationFilter.setFilterProcessesUrl(SecurityContact.SIGN_IN_URL);
//        return authenticationFilter;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration  = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        final UrlBasedCorsConfigurationSource sourse = new UrlBasedCorsConfigurationSource();
        sourse.registerCorsConfiguration("/**", configuration);
        return sourse;

    }


}
