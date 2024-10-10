package one.kiosk.controller;

import lombok.RequiredArgsConstructor;
import one.kiosk.dto.menu.RequestSaveMenuDto;
import one.kiosk.dto.response.ApiMessageResponse;
import one.kiosk.dto.response.ApiResponse;
import one.kiosk.entity.Member;
import one.kiosk.jwt.SecurityUtil;
import one.kiosk.service.MenuService;
import one.kiosk.vo.menu.MenuVo;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api2/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<?> saveMenu(@RequestBody RequestSaveMenuDto dto) {
        menuService.saveMenu(dto);
        return ResponseEntity.ok(new ApiMessageResponse("메뉴가 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<?> findAllMenu() {
        List<MenuVo> menulist =  menuService.findAllMenu();
        ApiResponse<?> response = new ApiResponse<>("메뉴를 전체 조회합니다.", menulist);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMenu(@PathVariable Long id) {
        MenuVo findMenu = menuService.findMenu(id);
        ApiResponse<?> response = new ApiResponse<>("메뉴를 조회합니다.", findMenu);
        return ResponseEntity.ok(response);
    }

}
