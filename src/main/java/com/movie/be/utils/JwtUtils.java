package com.movie.be.utils;

import com.movie.be.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "super_secret";
    Date date = new Date();

    public String generateJwt(User user){

        Claims claims = Jwts.claims().setIssuer(user.getId()).setIssuedAt(date);
        claims.put("email",user.getEmail());
        claims.put("name",user.getName());

      return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,secret).compact();

    }

    public void ValidateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }
        catch (Exception e){
            throw new Exception();
        }

    }
}
