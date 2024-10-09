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
    private Long imageId;

}
