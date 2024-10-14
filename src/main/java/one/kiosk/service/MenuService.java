package one.kiosk.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.menu.RequestSaveMenuDto;
import one.kiosk.dto.menu.RequestUpdateMenuDto;
import one.kiosk.entity.Member;
import one.kiosk.entity.Menu;
import one.kiosk.entity.Option;
import one.kiosk.enums.Status;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.jwt.SecurityUtil;
import one.kiosk.repository.MemberJpaRepository;
import one.kiosk.repository.menu.MenuJpaRepository;
import one.kiosk.vo.menu.MenuVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final OptionService optionService;
    private final MemberJpaRepository memberJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public void updateMenu(RequestUpdateMenuDto dto) {
        Optional<Menu> findMenu = menuJpaRepository.findById(dto.getMenuId());

        if(findMenu.isEmpty()) {
            throw new GlobalExceptionHandler.CustomException("메뉴를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        optionService.deleteByMenuId(findMenu.get().getId());
        List<Option> options = optionService.saveMenuOptions(findMenu.get(), dto.getOptions());
        entityManager.flush();
        findMenu.get().setAll(dto.getName(), dto.getPrice(), dto.getCategory(), dto.getStatus(), dto.getImgUrl(), options);

        menuJpaRepository.save(findMenu.get());
    }

    public void deleteMenu(Long id) {
        Optional<Menu> findMenu = menuJpaRepository.findById(id);
        Member findMember = SecurityUtil.getCurrentMember(memberJpaRepository);

        if(findMenu.isEmpty()) {
            throw new GlobalExceptionHandler.CustomException("메뉴를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        } else if (!findMenu.get().getMember().getId().equals(findMember.getId())) {
            throw new GlobalExceptionHandler.CustomException("다른 사용자가 등록한 메뉴입니다.", HttpStatus.BAD_REQUEST);
        }

//        optionService.deleteByMenuId(findMenu.get().getId());
        menuJpaRepository.deleteById(id);
    }

}
