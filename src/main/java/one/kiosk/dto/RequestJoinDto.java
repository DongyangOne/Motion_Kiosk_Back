package one.kiosk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import one.kiosk.entity.Member;
import one.kiosk.jwt.MemberRole;

@Data
@NoArgsConstructor
public class RequestJoinDto {

    private String username;
    private String password;
    private String passwordCheck;

    public Member toEntity(){
        return Member.builder()
                .username(this.username)
                .password(this.password)
                .role(MemberRole.USER)
                .build();
    }
}