package one.kiosk.service;

import lombok.RequiredArgsConstructor;
import one.kiosk.dto.RequestJoinDto;
import one.kiosk.dto.RequestLoginDto;
import one.kiosk.entity.Member;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.jwt.JWTUtil;
import one.kiosk.repository.MemberJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    // ID 중복 확인 메서드
    public boolean checkLoginIdDuplicate(String username) {
        return memberJpaRepository.existsByUsername(username);
    }

    // 회원가입 로직
    public void securityJoin(RequestJoinDto joinRequest) {
        // ID 중복 확인
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            throw new GlobalExceptionHandler.UserAlreadyExistsException("ID가 이미 존재합니다.");
        }

        // 비밀번호 암호화 후 저장
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        memberJpaRepository.save(joinRequest.toEntity());
    }

    // 로그인 로직
    public String login(RequestLoginDto loginRequest) {
        // 사용자 정보 조회
        Member findMember = memberJpaRepository.findByUsername(loginRequest.getUsername());

        // 사용자 정보가 없거나, 비밀번호가 일치하지 않는 경우 예외 처리
        if (findMember == null || !bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            throw new GlobalExceptionHandler.LoginFailedException("");
        }

        // 인증 성공 시 사용자 정보 반환
        return jwtUtil.createJwt(findMember.getUsername(), findMember.getCompany(), findMember.getRole().name());
    }
}
