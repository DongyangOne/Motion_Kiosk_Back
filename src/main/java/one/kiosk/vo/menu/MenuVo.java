package one.kiosk.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import one.kiosk.entity.Option;
import one.kiosk.enums.Category;
import one.kiosk.enums.Options;
import one.kiosk.enums.Status;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuVo {
    private Long id;
    private String name;
    private int price;
    private Category category;
    private Status status;
    private String img;
    private List<Option> options;

}
