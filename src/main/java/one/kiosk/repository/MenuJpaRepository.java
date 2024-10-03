package one.kiosk.repository;


import one.kiosk.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long> {
    MenuEntity findByMenuId(Long menuId);

}
