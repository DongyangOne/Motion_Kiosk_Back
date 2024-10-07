package one.kiosk.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.CustomUserDetails;
import one.kiosk.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 회원가입 및 로그인 경로는 JWT 검증을 하지 않음
        if (requestURI.equals("/users/join") || requestURI.equals("/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 비어있거나 "Bearer "로 시작하지 않는 경우
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 접두사를 제거하고 토큰 값만 추출
        String token;
        try {
            token = authorization.split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            // 잘못된 토큰 형식 예외 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("잘못된 토큰 형식입니다.");
            return;
        }

        try {
            // 토큰의 만료 여부 확인
            if (jwtUtil.isExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("토큰이 만료되었습니다. 새로 로그인해 주세요.");
                return;
            }

            // 토큰에서 사용자 정보 추출
            Long adminId = jwtUtil.getId(token);
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);
            String company = jwtUtil.getCompany(token);

            // Member 객체 생성
            Member member = new Member();
            member.setId(adminId);
            member.setUsername(username);
            member.setPassword("임시 비밀번호"); // 실제 비밀번호는 필요하지 않음
            member.setRole(MemberRole.valueOf(role)); // Enum 변환
            member.setCompany(company);

            // UserDetails에 회원 정보 객체 담기
            CustomUserDetails customUserDetails = new CustomUserDetails(member);

            // 스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 다음 필터로 request, response 넘겨줌
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // JWT 토큰 검증 중 기타 오류가 발생했을 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT 토큰 검증 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
