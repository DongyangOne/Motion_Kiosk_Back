package one.kiosk.dto;

import lombok.*;
import one.kiosk.entity.MenuOptionEntity;

@Data
@NoArgsConstructor
@Getter
@Setter
public class MenuOptionReturnDto {
    private String optionname;
    private int price;

    public MenuOptionReturnDto(String optionname, int price) {
        this.optionname = optionname;
        this.price = price;
    }
}

