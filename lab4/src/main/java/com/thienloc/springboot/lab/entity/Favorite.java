package com.thienloc.springboot.lab.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(
        name = "favorite",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"Userld", "Videold"})
        }
)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Userld")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Videold")
    private Video video;

    @Temporal(TemporalType.TIMESTAMP)
    private Date likeDate;
}