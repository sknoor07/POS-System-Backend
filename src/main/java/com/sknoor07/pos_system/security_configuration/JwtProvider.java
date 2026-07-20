package com.sknoor07.pos_system.security_configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    //static SecretKey key= Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateJwtToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles=populateAuthorities(authorities);
        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+864000))
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(getSigningKey())
                .compact();

    }

    public String getEmailFromJwtToken(String token) {
            token= token.replace("Bearer ", "");
            Claims claims= Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths= new HashSet<>();
        for(GrantedAuthority authority : authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}
