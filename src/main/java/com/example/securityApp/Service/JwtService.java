package com.example.securityApp.Service;

import com.example.securityApp.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "5367566B59703373357638792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    private UserRepository userRepository;

    private Key getSigningKey(){
        //Convert String Key to Bit[]
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claimsMap, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claimsMap)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) //Time Duration for User
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenNotExpired(token);
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractIssuedAt(String token){
        return extractClaims(token, Claims::getIssuedAt);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenNotExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }
}
