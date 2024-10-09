package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import one.kiosk.entity.MenuOptionEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuOptionReturnDto {
    private String optionname;
    private int price;

    //메뉴 Entity를 dto로 변환
    public static MenuOptionReturnDto toMenuOptionReturnDto(MenuOptionEntity menuOptionEntity) {
        MenuOptionReturnDto dto = new MenuOptionReturnDto();
        dto.setOptionname(menuOptionEntity.getOptionname());
        dto.setPrice(menuOptionEntity.getPrice());
        return dto;
    }
}

