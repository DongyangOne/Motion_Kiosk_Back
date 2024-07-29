package one.kiosk.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private String message;
}
