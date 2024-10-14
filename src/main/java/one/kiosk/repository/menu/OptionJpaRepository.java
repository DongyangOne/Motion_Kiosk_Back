package one.kiosk.repository.menu;

import one.kiosk.entity.Menu;
import one.kiosk.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    @Modifying
    @Query("DELETE FROM Option o WHERE o.menu.id = :menuId")
    void deleteByMenuId(@Param("menuId") Long menuId);
}
