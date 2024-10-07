package one.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.kiosk.entity.MenuEntity;
import one.kiosk.jwt.JWTUtil;

@Data
@AllArgsConstructor
@Builder
public class MenuUploadDto {
    private String menuname;
    private int price;
    private String category;
    private Long adminId;


    public MenuEntity toMenuEntity() {
        return MenuEntity.builder()
                .menuname(this.menuname)
                .price(this.price)
                .category(this.category)
                .adminId(this.adminId).build();
    }

}

