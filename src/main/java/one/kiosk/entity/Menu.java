package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import one.kiosk.enums.Category;
import one.kiosk.enums.Status;

import java.util.List;

@Entity
@Table(name = "tbl_menu")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member member;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Option> options;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String img;


}
