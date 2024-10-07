package one.kiosk.controller;

import lombok.RequiredArgsConstructor;
import one.kiosk.dto.ApiResponse;
import one.kiosk.dto.MenuReturnDto;
import one.kiosk.dto.MenuUpdateDto;
import one.kiosk.dto.MenuUploadDto;
import one.kiosk.entity.MenuEntity;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    //메뉴 등록
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestHeader("Authorization") String token, @RequestBody MenuUploadDto menuUploadDto) {

        //토큰의 "Bearer " 지우기
        String TokenUsername = token.replace("Bearer ", "");

        menuService.upload(menuUploadDto, TokenUsername);
        //메뉴 등록 성공
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"메뉴가 등록되었습니다.",null));
    }

    //메뉴 검색
    @GetMapping("search/{id}")
    public ResponseEntity<ApiResponse<MenuReturnDto>> upload(@RequestHeader("Authorization") String token,@PathVariable Long id) {

        //토큰의 "Bearer " 지우기
        String TokenUsername = token.replace("Bearer ", "");

        MenuEntity menuEntity = menuService.find(id,TokenUsername);

        if (menuEntity == null) {
            throw new GlobalExceptionHandler.MenuNotFoundException("메뉴를 찾을 수 없습니다");
        }
        //필요한 필드만 DTO로 변환
        MenuReturnDto menuReturnDto = new MenuReturnDto(
                menuEntity.getMenuname(),
                menuEntity.getPrice(),
                menuEntity.getCategory()
        );

        //메뉴 검색 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴 정보를 조회합니다",menuReturnDto));
    }

    //메뉴 전체조회
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<List<MenuUpdateDto>>> total(@RequestHeader("Authorization") String token) {
        //토큰의 "Bearer " 지우기
        String TokenUsername = token.replace("Bearer ", "");

        List<MenuUpdateDto> menuDtos = menuService.findAll(TokenUsername);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"전체 메뉴를 조회합니다",menuDtos));
    }

    //메뉴 수정
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> update(@RequestHeader("Authorization") String token, @RequestBody MenuUpdateDto menuUpdateDto) {

        //토큰의 "Bearer " 지우기
        String TokenUsername = token.replace("Bearer ", ""); //토큰의 "Bearer " 지우기

        menuService.update(menuUpdateDto,TokenUsername);
        //수정 완료
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴정보 수정이 완료되었습니다.",null));
    }

    //메뉴 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        //토큰의 "Bearer " 지우기
        String TokenUsername = token.replace("Bearer ", "");

        menuService.delete(id,TokenUsername);
        //삭제 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴를 삭제하였습니다.",null));
    }
}
