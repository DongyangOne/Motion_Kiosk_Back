package one.kiosk.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.*;
import one.kiosk.dto.response.ApiMessageResponse;
import one.kiosk.dto.response.ApiResponse;
import one.kiosk.dto.response.Datadto;
import one.kiosk.entity.Member;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final MemberJpaRepository memberJpaRepository;

    @PostMapping("/join")
    public ResponseEntity<ApiMessageResponse> join(@RequestBody RequestJoinDto joinRequest, BindingResult bindingResult) {

        // 회원가입 처리
        memberService.securityJoin(joinRequest);

        // 회원가입 성공
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiMessageResponse("회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Datadto>> login(@RequestBody RequestLoginDto loginDto) {
        // 로그인 로직 수행 후, JWT 토큰 발급
        String token = memberService.login(loginDto);

        // DataDto 객체에 토큰을 담음
        Datadto data = new Datadto(token);

        // ApiResponse를 통해 응답 메시지와 데이터를 전달
        ApiResponse<Datadto> response = new ApiResponse<>("로그인 성공", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Transactional
    public ResponseEntity<ApiResponse<MemberInfoDto>> memberInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            throw new GlobalExceptionHandler.CustomAuthenticationException("사용자 정보가 없습니다.");
        }

        String userName = userDetails.getUsername();

        if (userName == null) {
            throw new GlobalExceptionHandler.CustomAuthenticationException("사용자 ID가 없습니다.");
        }

        Member member = Optional.ofNullable(memberJpaRepository.findByUsername(userName))
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("회원 정보를 찾을 수 없습니다."));


        MemberInfoDto response = new MemberInfoDto();
        response.setUsername(member.getUsername());
        response.setCompany(member.getCompany());


        // 회원 정보 조회 성공
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 성공", response));
    }


    @GetMapping("/admin")
    public ResponseEntity<ApiMessageResponse> adminPage() {

        return ResponseEntity.ok(new ApiMessageResponse("인가 성공!"));
    }
}
