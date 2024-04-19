package com.example.ecom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY ="e54c0ec9a509e8406215d0ad7a03c570f45b24d8d54bfc07811cd61aca5d5640";
    public String extractUserNameFromToken(String jwtToken) {
        return  extractClaim(jwtToken,Claims::getSubject);
    }

    public <T> T extractClaim(String token , Function<Claims,T> tokenResolver){
        var claims = extractAllClaims(token);
         return tokenResolver.apply(claims);
    }


    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String createJwtToken(UserDetails userDetails){
        var claims = Jwts.claims().setSubject(userDetails.getUsername());
        var tokenIssuedAt = new Date();
        var tokenExpireDuration = 60*60*1000L;
        var tokenExpiredAt = new Date(tokenIssuedAt.getTime()+ TimeUnit.SECONDS.toMillis(tokenExpireDuration));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenIssuedAt)
                .setExpiration(tokenExpiredAt)
                .signWith(SignatureAlgorithm.HS256,getSignInKey())
                .compact();
    }

    public boolean isTokenExpired(String token){
       return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        return (!isTokenExpired(token) && extractUserNameFromToken(token).equals(userDetails.getUsername()));
    }

    private Key getSignInKey(){
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }

}
