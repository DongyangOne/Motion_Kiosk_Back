package one.kiosk.repository.menu;

import one.kiosk.entity.Member;
import one.kiosk.vo.menu.MenuVo;

import java.util.List;

public interface MenuCustomRepository {
    List<MenuVo> findMenuList(Member member);
    MenuVo findMenu(Long id, Member member);
}
