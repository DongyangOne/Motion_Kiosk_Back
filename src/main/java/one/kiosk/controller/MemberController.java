package one.kiosk.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.*;
import one.kiosk.entity.Member;
import one.kiosk.jwt.JWTUtil;
import one.kiosk.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> join(@RequestBody RequestJoinDto joinRequest, BindingResult bindingResult) {

        // 중복된 ID 체크
        if (memberService.checkLoginIdDuplicate(joinRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "ID가 이미 존재합니다.", null));
        }

        // 회원가입 처리
        memberService.securityJoin(joinRequest);

        // 회원가입 성공
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "회원가입 성공", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody RequestLoginDto loginRequest){

        // 로그인 처리
        Member member = memberService.login(loginRequest);

        // 로그인 실패 (ID 또는 비밀번호가 일치하지 않음)
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "ID 또는 비밀번호가 일치하지 않습니다!", null));
        }

        // JWT 생성
        String token = jwtUtil.createJwt(member.getUsername(), member.getCompany(), String.valueOf(member.getRole()));

        // 로그인 성공 및 JWT 반환
        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", token));
    }

    @GetMapping("/info")
    @Transactional
    public ResponseEntity<ApiResponse<InfoDto>> memberInfo(Authentication auth) {

        // 인증된 사용자 정보가 없을 때
        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "회원 정보를 찾을 수 없습니다.", null));
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Member loginMember = userDetails.getMember();

        // 필요한 필드만 DTO로 변환
        InfoDto memberInfoDto = new InfoDto(
                loginMember.getUsername(),
                loginMember.getCompany(),
                loginMember.getRole()
        );

        // 회원 정보 조회 성공
        return ResponseEntity.ok(new ApiResponse<>(true, "회원 정보 조회 성공", memberInfoDto));
    }


    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<String>> adminPage() {

        return ResponseEntity.ok(new ApiResponse<>(true, "인가 성공!", null));
    }
}
