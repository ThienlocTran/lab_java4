package com.thienloc.springboot.lab4.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(
        name = "Favorite",
        // Cấu hình ràng buộc duy nhất cho cặp cột (UserId, VideoId)
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"Userld", "Videold"})
        }
)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: Mối quan hệ Many-to-One với User (ID là Long)
    @ManyToOne
    @JoinColumn(name = "Userld") // Tên cột FK trong DB
    private User user; // <--- Đối tượng User (ID Long)

    // FK: Mối quan hệ Many-to-One với Video (ID là Long)
    @ManyToOne
    @JoinColumn(name = "Videold") // Tên cột FK trong DB
    private Video video; // <--- Đối tượng Video (ID Long)

    @Temporal(TemporalType.DATE)
    private Date likeDate;
}