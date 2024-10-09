package one.kiosk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_order_num")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderNum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member member;

    @Column(name = "total", nullable = false)
    private int total;

    @Column(name = "pay", nullable = false)
    private boolean pay;

}
