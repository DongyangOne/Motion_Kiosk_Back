package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.kiosk.entity.MenuEntity;
import one.kiosk.jwt.JWTUtil;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuReturnDto {

    private String menuname;
    private int price;
    private String category;
    private String imageUrl;
    private List<MenuOptionReturnDto> option;

    //메뉴 entity와 검색을 통해 나온 이미지의 url을 함께 dto에 저장. 전체 메뉴옵션을 함께 리턴
    public static MenuReturnDto toMenuReturnDto(MenuEntity menuEntity, String imageUrl, List<MenuOptionReturnDto> optionReturnDto) {
        MenuReturnDto dto = new MenuReturnDto();
        dto.setMenuname(menuEntity.getMenuname());
        dto.setPrice(menuEntity.getPrice());
        dto.setCategory(menuEntity.getCategory());
        dto.setImageUrl(imageUrl);
        dto.setOption(optionReturnDto);
        return dto;
    }
    

}

