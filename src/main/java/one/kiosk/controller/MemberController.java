package one.kiosk.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.*;
import one.kiosk.dto.response.ApiMessageResponse;
import one.kiosk.dto.response.ApiResponse;
import one.kiosk.dto.response.Datadto;
import one.kiosk.entity.Member;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.jwt.JWTUtil;
import one.kiosk.repository.MemberJpaRepository;
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
    private final MemberJpaRepository memberJpaRepository;

    @PostMapping("/join")
    public ResponseEntity<ApiMessageResponse> join(@RequestBody RequestJoinDto joinRequest, BindingResult bindingResult) {

        // 중복된 ID 체크
        if (memberService.checkLoginIdDuplicate(joinRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiMessageResponse("ID가 이미 존재합니다."));
        }

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

    @GetMapping("/info/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<Member>> memberInfo(@PathVariable Long id) {

        // ID로 회원 정보 조회
        Member member = memberJpaRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("회원 정보를 찾을 수 없습니다."));

        // 회원 정보 조회 성공
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 성공", member));
    }


    @GetMapping("/admin")
    public ResponseEntity<ApiMessageResponse> adminPage() {

        return ResponseEntity.ok(new ApiMessageResponse("인가 성공!"));
    }
}
