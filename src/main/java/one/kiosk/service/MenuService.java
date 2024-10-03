package one.kiosk.service;


import lombok.RequiredArgsConstructor;
import one.kiosk.dto.MenuUpdateDto;
import one.kiosk.dto.MenuUploadDto;
import one.kiosk.entity.MenuEntity;
import one.kiosk.repository.MenuJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;;

    //메뉴 등록
    public MenuEntity upload(MenuUploadDto menuUploadDto) {
        MenuEntity menuEntity = menuJpaRepository.save(menuUploadDto.toMenuEntity());
        //메뉴 등록 진행 중, 오류가 발생하면 null 반환
        try{
            return menuEntity;
        }
        catch(Exception e){
            return null;
        }
    }

    //메뉴 검색. dto변환 필요
    public MenuEntity find(Long id) {
        MenuEntity findMenu = menuJpaRepository.findById(id).orElse(null);
        return findMenu;
    }

    //메뉴 수정. 성공시 true, 실패시 false 반환
    public boolean update(MenuUpdateDto menuUpdateDto) {
        MenuEntity menuEntity = menuJpaRepository.findByMenuId(menuUpdateDto.getId());

        if(menuEntity != null){
            menuEntity.setMenuname(menuUpdateDto.getMenuname());
            menuEntity.setPrice(menuUpdateDto.getPrice());
            menuEntity.setCategory(menuUpdateDto.getCategory());
            menuEntity.setUpdate(LocalDateTime.now());

            menuJpaRepository.save(menuEntity);
            return true;
        }
        else{
            return false;
        }
    }

    //메뉴 삭제. 성공시 true, 실패시 false 반환
    public boolean delete(Long id) {
        MenuEntity menuEntity = menuJpaRepository.findById(id).orElse(null);
        if(menuEntity != null){
            menuJpaRepository.delete(menuEntity);
            return true;
        }
        else{
            return false;
        }
    }

    //전체조회
    public List<MenuUpdateDto> findAll() {
        List<MenuEntity> menuEntities = menuJpaRepository.findAll();
        List<MenuUpdateDto> menuUpdateDto = new ArrayList<>();

        //가게에 메뉴가 존재하지 않을 경우
        if(menuEntities.isEmpty()){
            return null;
        }

        for (MenuEntity menu : menuEntities) {
            menuUpdateDto.add(MenuUpdateDto.toMenuUpdateDto(menu));
        }
        return menuUpdateDto;
    }
}
