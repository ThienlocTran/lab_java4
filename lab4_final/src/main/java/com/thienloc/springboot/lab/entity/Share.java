package com.thienloc.springboot.lab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Share")
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: Mối quan hệ Many-to-One với User (ID là Long)
    @ManyToOne
    @JoinColumn(name = "Userld")
    private User user; // <--- Đối tượng User (ID Long)

    // FK: Mối quan hệ Many-to-One với Video (ID là Long)
    @ManyToOne
    @JoinColumn(name = "Videold")
    private Video video; // <--- Đối tượng Video (ID Long)

    private String emails;

    @Column(name = "shareDate", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date shareDate;
}