package one.kiosk.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.menu.RequestSaveMenuDto;
import one.kiosk.entity.Member;
import one.kiosk.entity.Menu;
import one.kiosk.enums.Status;
import one.kiosk.jwt.SecurityUtil;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.repository.menu.MenuJpaRepository;
import one.kiosk.vo.menu.MenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final OptionService optionService;
    private final MemberJpaRepository memberJpaRepository;

    public void saveMenu(RequestSaveMenuDto dto) {
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);
        Menu saveMenu = menuJpaRepository.save(Menu.builder().member(findMember).category(dto.getCategory()).name(dto.getName()).img(dto.getImgUrl()).status(Status.OnSale).price(dto.getPrice()).build());
        optionService.saveMenuOptions(saveMenu, dto.getOptions());
    }
    public List<MenuVo> findAllMenu() {
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);
        List<MenuVo> menuList = menuJpaRepository.findMenuList(findMember);
        return menuList;
    }

    public MenuVo findMenu(Long id) {
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);
        return menuJpaRepository.findMenu(id, findMember);
    }

}
