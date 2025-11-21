package com.thienloc.springboot.lab.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
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

    @Temporal(TemporalType.DATE)
    private Date shareDate;
}