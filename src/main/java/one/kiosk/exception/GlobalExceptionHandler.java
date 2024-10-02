package one.kiosk.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import one.kiosk.dto.response.ApiMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 공통적으로 사용되는 응답 객체 생성 메서드
    private ResponseEntity<ApiMessageResponse> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiMessageResponse(message));
    }

    // 내부 클래스: 사용자 정의 예외들
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class LoginFailedException extends RuntimeException {
        public LoginFailedException(String message) {
            super(message);
        }
    }

    public static class CustomAuthenticationException extends RuntimeException {
        public CustomAuthenticationException(String message) {
            super(message);
        }
    }

    // 예외 처리 메서드들
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiMessageResponse> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("UserNotFoundException: ", ex);
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiMessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.error("UserAlreadyExistsException: ", ex);
        return createResponse(HttpStatus.CONFLICT, "ID가 이미 존재합니다.");
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiMessageResponse> handleLoginFailedException(LoginFailedException ex) {
        logger.error("LoginFailedException: ", ex);
        return createResponse(HttpStatus.UNAUTHORIZED, "ID 또는 비밀번호가 일치하지 않습니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiMessageResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error("ExpiredJwtException: ", ex);
        return createResponse(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다. 새로 로그인 해 주세요.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiMessageResponse> handleJwtException(JwtException ex) {
        logger.error("JwtException: ", ex);
        return createResponse(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiMessageResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: ", ex);
        return createResponse(HttpStatus.BAD_REQUEST, "잘못된 요청: " + ex.getMessage());
    }

    // 추가: AccessDeniedException 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiMessageResponse> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException: ", ex);
        return createResponse(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    // 추가: AuthenticationException 처리
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiMessageResponse> handleAuthenticationException(AuthenticationException ex) {
        logger.error("AuthenticationException: ", ex);
        return createResponse(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.");
    }

    // 추가: 일반적인 예외 처리 (옵션)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiMessageResponse> handleGeneralException(Exception ex) {
        logger.error("Unhandled Exception: ", ex);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }
}
