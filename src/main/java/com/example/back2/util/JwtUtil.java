package com.example.back2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // JWT'nin imza anahtarı
    private final String SECRET_KEY = "secret";  // Bu anahtarı güvenli bir şekilde saklamalısınız (örneğin environment variable)

    // Token'ı oluşturma
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // Kullanıcı email'ini subject olarak ekliyoruz
                .setIssuedAt(new Date())  // Token'ın oluşturulma zamanını ekliyoruz
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token'ın geçerlilik süresi (1 saat)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // JWT'yi imzalıyoruz
                .compact();
    }

    // Token'dan kullanıcı email'ini çıkarma
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Token'dan Claim bilgilerini çıkarma
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Token'ın geçerliliğini kontrol etme
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Token doğrulama (geçerliliğini kontrol etme)
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
