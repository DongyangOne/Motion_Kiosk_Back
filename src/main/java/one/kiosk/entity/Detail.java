package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import one.kiosk.enums.Details;

@Entity
@Table(name = "tbl_detail")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detail extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "detail", nullable = false)
    private Details detail;

    @Column(name = "price", nullable = false)
    private int price;

}
