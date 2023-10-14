package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.jwt.service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final String SECRET_KEY = "12345678"; 

    public String generateToken(String username) {
    	 Timestamp now = new Timestamp(System.currentTimeMillis());
         Timestamp expiration = new Timestamp(now.getTime() + 3600000); // 1 hora

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); 
    }
}
