package one.kiosk.repository.menu;

import one.kiosk.entity.Member;
import one.kiosk.entity.Menu;
import one.kiosk.enums.Status;
import one.kiosk.vo.menu.MenuVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<Menu, Long>, MenuCustomRepository{
    Optional<Menu> findById(Long id);
}
