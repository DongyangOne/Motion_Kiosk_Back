package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "tbl_menuoption")
public class MenuOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuop_id")
    private Long menuopid;

    @Column(name = "optionname")
    private String optionname;

    @Column(name =  "price")
    private int price;

}
