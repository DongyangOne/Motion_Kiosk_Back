package one.kiosk.controller;

import lombok.RequiredArgsConstructor;
import one.kiosk.dto.*;
import one.kiosk.entity.Member;
import one.kiosk.jwt.JWTUtil;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/")
    public HomeResponseDto home() {
        HomeResponseDto response = new HomeResponseDto();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        response.setUsername(username);
        response.setRole(role);

        return response;
    }

    @GetMapping("/join")
    public RequestJoinDto joinPage() {
        return new RequestJoinDto();
    }


    @PostMapping("/join")
    public JoinResponseDto join(@RequestBody RequestJoinDto joinRequest, BindingResult bindingResult) {
        JoinResponseDto response = new JoinResponseDto();

        // ID 중복 여부 확인
        if (memberService.checkLoginIdDuplicate(joinRequest.getUsername())) {
            response.setMessage("ID가 존재합니다.");
            return response;
        }

        // 비밀번호 = 비밀번호 체크 여부 확인
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            response.setMessage("비밀번호가 일치하지 않습니다.");
            return response;
        }

        // 에러가 존재하지 않을 시 joinRequest 통해서 회원가입 완료
        memberService.securityJoin(joinRequest);

        // 회원가입 시 홈 화면으로 이동
        response.setMessage("회원가입 성공");
        return response;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody RequestLoginDto loginRequest) {
        LoginResponseDto response = new LoginResponseDto();

        Member member = memberService.login(loginRequest);

        if (member == null) {
            response.setMessage("ID 또는 비밀번호가 일치하지 않습니다!");
            return response;
        }

        String token = jwtUtil.createJwt(member.getUsername(), String.valueOf(member.getRole()));
        response.setToken(token);
        response.setMessage("로그인 성공");
        return response;
    }

}
