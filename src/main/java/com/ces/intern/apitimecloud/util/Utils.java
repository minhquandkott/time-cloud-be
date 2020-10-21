package com.ces.intern.apitimecloud.util;

import com.ces.intern.apitimecloud.ApplicationContext;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.request.UserLoginRequest;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import sun.awt.AppContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static List<String> generateJWTToken(UserDTO loginUser){
        UserService userService = (UserService) ApplicationContext.getBean("userServiceImpl");

        String token = Jwts.builder()
                .claim("email", loginUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityContact.EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SecurityContact.TOKEN_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return Arrays.asList(token, loginUser.getId()+"");
    }

    public static String toNumbersOfDay(String date, int days) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(date));
        calendar.add(Calendar.DATE, days);
        String nextDay = simpleDateFormat.format(calendar.getTime());
        return nextDay;
    }

    public static String toFirstDayOfWeek(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(date));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -(6-dayOfWeek));
        String firstDay = simpleDateFormat.format(calendar.getTime());
        return firstDay;
    }
}
