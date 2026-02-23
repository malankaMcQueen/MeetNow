package com.example.meetnow.service.jwt;

import com.example.meetnow.exception.InvalidCredentialsException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;


@Slf4j
@Component
public class JwtService {

    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;


    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;

//    @Value("${jwt.secret.auth.telegram}")
//    private String jwtAuthTelegramSecret;


    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, getSecretKey(jwtAccessSecret));
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    private Key getSecretKey(String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractAccessSubject(@NonNull String token) {
        Claims claims = getClaims(token, getSecretKey(jwtAccessSecret));
        return claims.getSubject();
    }
    public String extractRefreshSubject(@NonNull String token) {
        Claims claims = getClaims(token, getSecretKey(jwtRefreshSecret));
        return claims.getSubject();
    }

    public <T> T extractAccessClaim(@NonNull String token, @NonNull String claimKey, @NonNull Class<T> claimType) {
        Claims claims = getClaims(token, getSecretKey(jwtAccessSecret));
        return claims.get(claimKey, claimType); // Извлечение произвольного claim
    }
    public <T> T extractRefreshClaim(@NonNull String token, @NonNull String claimKey, @NonNull Class<T> claimType) {
        Claims claims = getClaims(token, getSecretKey(jwtRefreshSecret));
        return claims.get(claimKey, claimType); // Извлечение произвольного claim
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractAllClaims(@NonNull String token, boolean isAccessToken) {
        String secret = isAccessToken ? jwtAccessSecret : jwtRefreshSecret;
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    static public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;

        Integer userId = (Integer) authentication.getPrincipal();

        if (userId == null) {
            throw new InvalidCredentialsException("User dont auth");
        }
        return Long.valueOf(userId);
    }

//    public boolean validateTelegramAuthToken(String token) {
//        return validateToken(token, getSecretKey(jwtAuthTelegramSecret));
//    }
//
//    public <T> T extractTelegramAuthTokenClaim(String token, String claimKey, Class<T> claimType) {
//        Claims claims = getClaims(token, getSecretKey(jwtAuthTelegramSecret));
//        return claims.get(claimKey, claimType); // Извлечение произвольного claim
//    }

}