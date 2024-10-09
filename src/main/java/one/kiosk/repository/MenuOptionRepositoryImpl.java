package one.kiosk.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import one.kiosk.dto.MenuOptionReturnDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuOptionRepositoryImpl implements MenuOptionRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    //쿼리문 실행하여 리스트에 한꺼번에 담기
    @Override
    public List<MenuOptionReturnDto> findMenuOptions() {
        String jpql = "SELECT new one.kiosk.dto.MenuOptionReturnDto(m.optionname, m.price) FROM MenuOptionEntity m";
        return em.createQuery(jpql, MenuOptionReturnDto.class).getResultList();
    }
}
