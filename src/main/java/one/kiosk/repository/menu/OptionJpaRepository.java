package one.kiosk.repository.menu;

import one.kiosk.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {
}
