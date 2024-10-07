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
public class MenuReturnDto {
    private String menuname;
    private int price;
    private String category;
    

}

