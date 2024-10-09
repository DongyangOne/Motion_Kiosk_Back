package one.kiosk.repository;

import one.kiosk.entity.MenuOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOptionEntity, Long> {

}
