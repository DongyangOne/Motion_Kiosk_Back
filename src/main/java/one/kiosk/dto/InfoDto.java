package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import one.kiosk.jwt.MemberRole;

@Data
@AllArgsConstructor
public class InfoDto {
    private String username;
    private String company;
    private MemberRole role;
}