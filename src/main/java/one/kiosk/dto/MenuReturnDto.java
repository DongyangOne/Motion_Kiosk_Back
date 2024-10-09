package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.kiosk.entity.MenuEntity;
import one.kiosk.jwt.JWTUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuReturnDto {

    private String menuname;
    private int price;
    private String category;
    private String imageUrl;

    //메뉴 entity와 검색을 통해 나온 이미지의 url을 함께 dto에 저장
    public static MenuReturnDto toMenuReturnDto(MenuEntity menuEntity, String imageUrl) {
        MenuReturnDto dto = new MenuReturnDto();
        dto.setMenuname(menuEntity.getMenuname());
        dto.setPrice(menuEntity.getPrice());
        dto.setCategory(menuEntity.getCategory());
        dto.setImageUrl(imageUrl);
        return dto;
    }
    

}

