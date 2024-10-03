package one.kiosk.dto;

import lombok.*;
import one.kiosk.entity.MenuEntity;

@Data
@AllArgsConstructor
@Getter
@Builder
public class MenuUpdateDto {

    private Long id;
    private String menuname;
    private int price;
    private String category;

    //findAll을 통해 받은 MenuEntity들을 Dto로 변환 후 리턴
    public static MenuUpdateDto toMenuUpdateDto(MenuEntity menuEntity) {
        return MenuUpdateDto.builder()
                .id(menuEntity.getMenuId())
                .menuname(menuEntity.getMenuname())
                .price(menuEntity.getPrice())
                .category(menuEntity.getCategory())
                .build();
    }

}
