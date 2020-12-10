package com.project.covid19.controller;

import com.project.covid19.util.Util;
import com.project.covid19.component.ApplicationContextProvider;
import com.project.covid19.constants.SecurityConstants;
import com.project.covid19.entity.Member;
import com.project.covid19.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@Log
@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080", allowedHeaders = "*")
public class RefreshTokenController {

    @PostMapping(SecurityConstants.REFRESH_TOKEN_URL)
    public ResponseEntity<String> refreshToken(HttpServletRequest request){
        String message = "Fail";

        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if(!Util.isEmptyString(token)){
            byte[] signingKey = SecurityConstants.JWT_SECRET_REFRESH.getBytes();
            token = token.replace("Bearer ", "");

            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token);

            long no = Long.valueOf(parsedToken
                    .getBody()
                    .getSubject());

            ApplicationContext context = ApplicationContextProvider.getApplicationContext();
            MemberRepository repo = context.getBean(MemberRepository.class);
            Member Member = repo.findById(no).get();

            if(Member.getRefreshToken().equals(token)){
                byte[] accessKey = SecurityConstants.JWT_SECRET.getBytes();
                String accessToken = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(accessKey), SignatureAlgorithm.HS512)
                        .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                        .setIssuer(SecurityConstants.TOKEN_ISSUER)
                        .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                        .setSubject("" + no)
                        .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                        .compact();

                try {
                    JSONObject json = new JSONObject();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(SecurityConstants.ACCESS_TOKEN_KEY, accessToken);

                    json.put("jwt", map);

                    return new ResponseEntity<String>(json.toJSONString(), HttpStatus.OK);
                } catch (Exception e){
                    log.info(ExceptionUtils.getStackTrace(e));
                }
            }
        }

        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
