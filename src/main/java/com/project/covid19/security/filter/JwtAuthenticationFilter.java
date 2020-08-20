package com.project.covid19.security.filter;

import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.covid19.Util.Util;
import com.project.covid19.constants.SecurityConstants;
import com.project.covid19.security.custom.CustomUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String body = null;
        JSONObject json = null;
        try {
            body = Util.getBodyFromHttpRequest(request);
            json = (JSONObject) JSONValue.parse(body);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        String id = json.get("id").toString();
        String password = json.get("password").toString();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        CustomUser user = ((CustomUser) authentication.getPrincipal());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject("" + user.getMember().getUserNo())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }
}