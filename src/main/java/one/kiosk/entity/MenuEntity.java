package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;
import one.kiosk.jwt.MemberRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

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

    //jwt 토큰에서 추출한 id를 저장
    @Column(name = "admin_id", nullable = false)
    private Long adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")  // 외래키 설정
    private Image imageId;




}
