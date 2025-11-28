package vti.dtn.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.repo.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${app.auth.tokenSecret}")
    private String secretKey;

    @Value("${app.auth.tokenExpiration}")
    private long accessExpiration;

    @Value("${app.auth.refreshTokenExpiration}")
    private long refreshExpiration;

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==========================================================
    // GENERATE ACCESS TOKEN
    // ==========================================================
    public String generateAccessToken(String username) {
        return buildToken(username, accessExpiration);
    }

    // ==========================================================
    // GENERATE REFRESH TOKEN
    // ==========================================================
    public String generateRefreshToken(String username) {
        return buildToken(username, refreshExpiration);
    }

    // ==========================================================
    // BUILD TOKEN
    // ==========================================================
    private String buildToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==========================================================
    // GET SECRET KEY
    // ==========================================================
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ==========================================================
    // PARSE CLAIMS
    // ==========================================================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        Date exp = extractAllClaims(token).getExpiration();
        return exp.before(new Date());
    }

    // ==========================================================
    // VALIDATE TOKEN
    // ==========================================================
    public boolean isValid(String token) {
        try {
            if (isExpired(token)) {
                return false;
            }

            String username = extractUsername(token);
            if (!StringUtils.hasText(username)) {
                return false;
            }

            Optional<UserEntity> user = userRepository.findByUsername(username);
            return user.isPresent();

        } catch (Exception e) {
            return false;
        }
    }
}
