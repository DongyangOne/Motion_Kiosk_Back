package one.kiosk.service;

import lombok.AllArgsConstructor;
import one.kiosk.dto.CustomUserDetails;
import one.kiosk.entity.Member;
import one.kiosk.repository.MemberJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member memberData = memberJpaRepository.findByUsername(username);

        if(memberData != null){
            return new CustomUserDetails(memberData);
        }

        return null;
    }
}
