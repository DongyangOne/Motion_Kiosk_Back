package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;
import one.kiosk.jwt.MemberRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@Table(name = "tbl_menu")
public class MenuEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menuname", nullable = false)
    private String menuname;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "category", nullable = false)
    private String category;


    @CreatedDate
    @Column(name = "create", updatable = false)
    private LocalDateTime create;

    @LastModifiedDate
    @Column(name = "update")
    private LocalDateTime update;
}
