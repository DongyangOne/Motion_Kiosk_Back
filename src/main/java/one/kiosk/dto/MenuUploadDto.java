package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.kiosk.entity.MenuEntity;

@Data
@AllArgsConstructor
@Builder
public class MenuUploadDto {
    private String menuname;
    private int price;
    private String category;


    public MenuEntity toMenuEntity() {
        return MenuEntity.builder()
                .menuname(this.menuname)
                .price(this.price)
                .category(this.category).build();
    }

}

