package one.kiosk.repository;


import one.kiosk.dto.MenuReturnDto;
import one.kiosk.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long>, MenuJpaRpositoryCustom {
    MenuEntity findByMenuId(Long menuId);

    List<MenuEntity> findAllByAdminId(Long adminId);

}
