package com.ces.intern.apitimecloud.security.filter;

import com.ces.intern.apitimecloud.ApplicationContext;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.http.request.UserLoginRequest;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserLoginRequest user = new UserLoginRequest();
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>()
                )
        );

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((User)authResult.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityContact.EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SecurityContact.TOKEN_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        UserService userService = (UserService) ApplicationContext.getBean("userServiceImpl");
        UserDTO user = userService.findByEmail(email);
        response.setHeader(SecurityContact.HEADER_STRING, SecurityContact.TOKEN_PREFIX+ token);
        response.setHeader(SecurityContact.HEADER_USERID, user.getId()+"");
        chain.doFilter(request,response);
    }
}
