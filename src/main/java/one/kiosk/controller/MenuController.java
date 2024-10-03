package one.kiosk.controller;

import lombok.RequiredArgsConstructor;
import one.kiosk.dto.ApiResponse;
import one.kiosk.dto.MenuUpdateDto;
import one.kiosk.dto.MenuUploadDto;
import one.kiosk.entity.MenuEntity;
import one.kiosk.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    //메뉴 등록
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestBody MenuUploadDto menuUploadDto) {
        MenuEntity menuEntity = menuService.upload(menuUploadDto);

        //메뉴 등록 실패
        if(menuEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false,"메뉴 등록에 실패하였습니다.",null));
        }

        //메뉴 등록 성공
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"메뉴가 등록되었습니다.",null));
    }

    //메뉴 검색
    @GetMapping("search/{id}")
    public ResponseEntity<ApiResponse<MenuUploadDto>> upload(@PathVariable Long id) {
        MenuEntity menuEntity = menuService.find(id);

        //찾고자 하는 메뉴가 없을 경우
        if(menuEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,"메뉴를 찾을 수 없습니다",null));
        }
        //필요한 필드만 DTO로 변환
        MenuUploadDto menuUploadDto = new MenuUploadDto(
                menuEntity.getMenuname(),
                menuEntity.getPrice(),
                menuEntity.getCategory()
        );

        //메뉴 검색 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴 정보를 조회합니다",menuUploadDto));
    }

    //메뉴 전체조회
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<List<MenuUpdateDto>>> total() {
        List<MenuUpdateDto> menuDtos = menuService.findAll();

        //등록된 메뉴가 없을경우
        if(menuDtos==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,"등록된 메뉴가 없습니다",null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"전체 메뉴를 조회합니다",menuDtos));
    }

    //메뉴 수정
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> update(@RequestBody MenuUpdateDto menuUpdateDto) {
        boolean success = menuService.update(menuUpdateDto);

        //수정하고자 하는 메뉴가 없을경우
        if(!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false,"메뉴를 수정하지 못했습니다.",null));
        }

        //수정 완료
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴정보 수정이 완료되었습니다.",null));
    }

    //메뉴 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        boolean success = menuService.delete(id);

        //삭제 실패
        if(!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false,"메뉴를 삭제하지 못했습니다",null));
        }

        //삭제 성공
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"메뉴를 삭제하였습니다.",null));
    }
}
