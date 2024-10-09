package one.kiosk.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.menu.RequestSaveMenuDto;
import one.kiosk.entity.Image;
import one.kiosk.entity.Member;
import one.kiosk.entity.Menu;
import one.kiosk.enums.Status;
import one.kiosk.exception.CustomException;
import one.kiosk.jwt.SecurityUtil;
import one.kiosk.repository.ImageRepository;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.repository.menu.MenuJpaRepository;
import one.kiosk.vo.menu.MenuVo;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final OptionService optionService;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void saveMenu(RequestSaveMenuDto dto) {
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);
        Menu saveMenu = menuJpaRepository.save(Menu.builder().member(findMember).category(dto.getCategory()).name(dto.getName()).img(dto.getImgUrl()).status(Status.OnSale).price(dto.getPrice()).build());
        optionService.saveMenuOptions(saveMenu, dto.getOptions());
    }
    @Transactional
    public List<MenuVo> findAllMenu() {
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);
        List<MenuVo> menuList = menuJpaRepository.findMenuList(findMember);
        return menuList;
    }

}
