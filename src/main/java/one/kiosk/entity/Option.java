package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;
import one.kiosk.enums.Options;

@Entity
@Table(name = "tbl_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "options", nullable = false)
    private Options option;


}
