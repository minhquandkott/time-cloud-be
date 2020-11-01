package com.ces.intern.apitimecloud.util;

import com.ces.intern.apitimecloud.ApplicationContext;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.security.config.SecurityContact;
import com.ces.intern.apitimecloud.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        if(dayOfWeek==1){
            calendar.add(Calendar.DATE, -6);
        }else{
            calendar.add(Calendar.DATE, (2-dayOfWeek));
        }
        String firstDay = simpleDateFormat.format(calendar.getTime());
        return firstDay;
    }

    public static List<String> getFieldName(Class c){
        List<Field> fields = Arrays.asList(c.getDeclaredFields());
        return fields.stream().map(field -> field.getName()).collect(Collectors.toList()) ;
    }

    public static boolean containFiledName(Class c, String fieldName){
        List<String> fields = getFieldName(c);
        List<String> supperClassFields = getFieldName(c.getSuperclass());

        return fields.stream().anyMatch(field -> field.equals(fieldName)) ||
                supperClassFields.stream().anyMatch(field -> field.equals(fieldName));
    }
}
