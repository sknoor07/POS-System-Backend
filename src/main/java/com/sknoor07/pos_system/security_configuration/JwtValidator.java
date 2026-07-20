package com.sknoor07.pos_system.security_configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
public class JwtValidator extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtHeader=request.getHeader(JwtConstant.JWT_HEADER);
        if(jwtHeader!=null){
            String bearerToken= jwtHeader.replace("Bearer ", "");
            try{
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
                Claims claims= Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(bearerToken)
                        .getPayload();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth=new UsernamePasswordAuthenticationToken(email,null ,auths);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch(Exception e){
                throw new BadCredentialsException("Invalid JWT...");
            }
        }
        filterChain.doFilter(request, response);


    }
}
