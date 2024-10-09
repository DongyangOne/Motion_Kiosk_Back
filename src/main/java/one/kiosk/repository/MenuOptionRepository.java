package one.kiosk.repository;

import one.kiosk.entity.MenuOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionRepository extends JpaRepository<MenuOptionEntity, Long>, MenuOptionRepositoryCustom {
}
