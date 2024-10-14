package one.kiosk.dto.menu;

import lombok.Data;
import one.kiosk.enums.Category;
import one.kiosk.enums.Options;
import one.kiosk.enums.Status;

import java.util.List;

@Data
public class RequestUpdateMenuDto {
    private Long menuId;
    private String name;
    private int price;
    private Category category;
    private Status status;
    private String imgUrl;
    private List<Options> options;

}
