package one.kiosk.repository;


import one.kiosk.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long> {
    MenuEntity findByMenuId(Long menuId);

    List<MenuEntity> findAllByAdminId(Long adminId);

}
