package com.ces.intern.apitimecloud.security.filter;

import com.ces.intern.apitimecloud.security.config.SecurityContact;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityContact.HEADER_STRING);
        if(header == null || !header.startsWith(SecurityContact.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = null;
        try {
            authentication = getAuthentication(header);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);

    }

    private Authentication getAuthentication(String header) throws IllegalAccessException {

        String token  = header.replace(SecurityContact.TOKEN_PREFIX, "");

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SecurityContact.TOKEN_SECRET.getBytes()))
                    .build().parseClaimsJws(token);

            String email = claimsJws.getBody().getSubject();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    new ArrayList<>()
            );
            return authentication;
        }catch (JwtException e){
            throw new IllegalAccessException("Token cant be truest");
        }

    }


}
