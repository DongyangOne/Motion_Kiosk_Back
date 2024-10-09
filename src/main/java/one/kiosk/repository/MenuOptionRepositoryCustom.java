package one.kiosk.repository;

import one.kiosk.dto.MenuOptionReturnDto;
import one.kiosk.entity.MenuOptionEntity;

import java.util.List;

public interface MenuOptionRepositoryCustom {

    //쿼리문 실행을 위한 커스텀 리포지토리
    List<MenuOptionReturnDto> findMenuOptions();
}
