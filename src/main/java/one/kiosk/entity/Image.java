package one.kiosk.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageUrl;

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime create;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime update;
}
