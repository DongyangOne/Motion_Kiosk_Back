package one.kiosk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class DateEntity {

    @CreatedDate
    @Column(name = "created", updatable = false)
    private LocalDateTime create;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime update;
}
