package one.kiosk.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import one.kiosk.dto.response.ApiMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    // 예외 처리 메서드들
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiMessageResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiMessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return createResponse(HttpStatus.CONFLICT, "ID가 이미 존재합니다.");
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiMessageResponse> handleLoginFailedException(LoginFailedException ex) {
        return createResponse(HttpStatus.UNAUTHORIZED, "ID 또는 비밀번호가 일치하지 않습니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiMessageResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return createResponse(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다. 새로 로그인 해 주세요.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiMessageResponse> handleJwtException(JwtException ex) {
        return createResponse(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiMessageResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return createResponse(HttpStatus.BAD_REQUEST, "잘못된 요청: " + ex.getMessage());
    }
}
