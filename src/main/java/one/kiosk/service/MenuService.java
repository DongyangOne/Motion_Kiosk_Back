package one.kiosk.service;

import lombok.RequiredArgsConstructor;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.repository.menu.MenuJpaRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final MemberJpaRepository memberJpaRepository;



}
