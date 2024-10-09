package one.kiosk.jwt;

import one.kiosk.entity.Member;
import one.kiosk.repository.MemberJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

    public static Member getCurrentMember(MemberJpaRepository memberJpaRepository) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No Authentication information");
        }

        return memberJpaRepository.findByUsername(authentication.getName());

    }
}
