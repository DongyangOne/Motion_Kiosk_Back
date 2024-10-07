package one.kiosk.service;


import lombok.RequiredArgsConstructor;
import one.kiosk.dto.MenuUpdateDto;
import one.kiosk.dto.MenuUploadDto;
import one.kiosk.entity.MenuEntity;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.jwt.JWTUtil;
import one.kiosk.repository.MenuJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;
    private final JWTUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class); // Logger 생성

    //메뉴 등록
    public MenuEntity upload(MenuUploadDto menuUploadDto, String token) {

        //토큰에서 유저 id 추출 후 메뉴 entity에 저장
        Long adminId = jwtUtil.getId(token);

        menuUploadDto.setAdminId(adminId);
        MenuEntity menuEntity = menuJpaRepository.save(menuUploadDto.toMenuEntity());
        //메뉴 등록 진행 중, 오류가 발생하면 throw error
        try{
            return menuEntity;
        }
        catch(Exception e){
            throw new GlobalExceptionHandler.MenuUplaodException("메뉴 등록에 실패하였습니다");
        }
    }

    //메뉴 검색. dto변환 필요
    public MenuEntity find(Long id,String token) {
        Long adminId = jwtUtil.getId(token);

        Optional<MenuEntity> OpmenuEntity = menuJpaRepository.findById(id);

        if(OpmenuEntity.isPresent()){
            MenuEntity menuEntity = OpmenuEntity.get();

            if(!menuEntity.getAdminId().equals(adminId)){
                throw new GlobalExceptionHandler.UserUnmatchException("해당 메뉴에 접근 권한이 없습니다.");
            }
            return menuEntity;
        }
        return null;
    }

    //메뉴 수정. 성공시 true, 실패시 throw error
    public void update(MenuUpdateDto menuUpdateDto, String token) {
        Long adminId = jwtUtil.getId(token);

        Optional<MenuEntity> OpMenuEntity = Optional.ofNullable(menuJpaRepository.findByMenuId(menuUpdateDto.getId()));

        if (OpMenuEntity.isPresent()) {
            // optional에서 entity값 추출
            MenuEntity menuEntity = OpMenuEntity.get();

            //findByMenuId를 통해 찾은 menuEntity의 menuId과 토큰에서 추출한 adminId를 비교. 일치하지 않으면 에러 throw
            if(!menuEntity.getAdminId().equals(adminId)) {
                throw new GlobalExceptionHandler.UserUnmatchException("해당 메뉴에 접근 권한이 없습니다.");
            }

            menuEntity.setMenuname(menuUpdateDto.getMenuname());
            menuEntity.setPrice(menuUpdateDto.getPrice());
            menuEntity.setCategory(menuUpdateDto.getCategory());
            menuEntity.setUpdate(LocalDateTime.now());

            menuJpaRepository.save(menuEntity);

        } else {
            //에러 발생시 throw
            throw new GlobalExceptionHandler.MenuUpdateException("메뉴를 수정하지 못했습니다");
        }
    }

    //메뉴 삭제. 실패시 throw error
    public void delete(Long id, String token) {
        Long adminId = jwtUtil.getId(token);
        Optional<MenuEntity> OpMenuEntity = menuJpaRepository.findById(id);

        if(OpMenuEntity.isPresent()){
            MenuEntity menuEntity = OpMenuEntity.get();

            //findByMenuId를 통해 찾은 menuEntity의 menuId과 토큰에서 추출한 adminId를 비교. 일치하지 않으면 에러 throw
            if(!menuEntity.getAdminId().equals(adminId)) {
                throw new GlobalExceptionHandler.UserUnmatchException("해당 메뉴에 접근 권한이 없습니다.");
            }
            menuJpaRepository.delete(menuEntity);
        }
        else{
            throw new GlobalExceptionHandler.MenuDeleteException("메뉴를 삭제하지 못했습니다.");
        }
    }

    //전체조회
    public List<MenuUpdateDto> findAll(String token) {
        Long adminId = jwtUtil.getId(token);
        List<MenuEntity> menuEntities = menuJpaRepository.findAllByAdminId(adminId);
        List<MenuUpdateDto> menuUpdateDto = new ArrayList<>();

        //가게에 메뉴가 존재하지 않을 경우
        if(menuEntities.isEmpty()){
            throw new GlobalExceptionHandler.MenuNotExistException("등록된 메뉴가 없습니다");
        }

        for (MenuEntity menu : menuEntities) {
            menuUpdateDto.add(MenuUpdateDto.toMenuUpdateDto(menu));
        }
        return menuUpdateDto;
    }

}
