package one.kiosk.dto.menu;

import lombok.Data;
import one.kiosk.enums.Category;
import one.kiosk.enums.Options;

import java.util.List;

@Data
public class RequestSaveMenuDto {

    private String name;
    private int price;
    private Category category;
    private String imgUrl;
    private List<Options> options;

}
