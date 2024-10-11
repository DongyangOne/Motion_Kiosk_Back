package one.kiosk.repository;

import one.kiosk.dto.MenuReturnDto;

import java.util.List;

public interface MenuJpaRpositoryCustom {
    List<MenuReturnDto> findAllMenuWithOptionsByAdminId(Long adminId);

}
