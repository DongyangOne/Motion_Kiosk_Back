package one.kiosk.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import one.kiosk.entity.Member;
import one.kiosk.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
    private static final long CLOCK_SKEW = 1000 * 60 * 30; // 30분의 클럭 스큐 허용

    private final SecretKey secretKey;

    // SecretKey를 생성자에서 설정
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // secret 값을 기반으로 SecretKey 생성
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // HS256 알고리즘에 적합한 SecretKey 생성
    }

    // JWT 토큰에서 username 추출
    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    // JWT 토큰에서 role 추출
    public String getRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // JWT 토큰에서 company 추출
    public String getCompany(String token) {
        return extractAllClaims(token).get("company", String.class);
    }

    // 토큰의 만료 여부를 확인하는 메서드
    public Boolean isExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            System.err.println("JWT 토큰이 만료되었습니다: " + e.getMessage());
            throw e; // 만료된 토큰 예외를 그대로 던집니다.
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("토큰 만료 여부 확인 중 오류가 발생했습니다: " + e.getMessage());
            return true; // 예외 발생 시 만료된 것으로 간주
        }
    }

    // 모든 클레임을 추출하는 메서드
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // SecretKey로 서명 키 설정
                    .setAllowedClockSkewSeconds(CLOCK_SKEW / 1000)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT 토큰이 만료되었습니다: " + e.getMessage());
            throw e; // 만료된 토큰 예외를 그대로 던집니다.
        } catch (JwtException e) {
            System.err.println("JWT 토큰을 파싱하는 중 오류가 발생했습니다: " + e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.err.println("잘못된 JWT 토큰 인자입니다: " + e.getMessage());
            throw new IllegalArgumentException("잘못된 토큰 또는 인자입니다.");
        }
    }

    // JWT 토큰 생성 메서드
    public String createJwt(String username, String company, String role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("company", company)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 생성일자 설정
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료일자 설정
                .signWith(secretKey, SignatureAlgorithm.HS256) // SecretKey로 서명 설정
                .compact();
    }


}
