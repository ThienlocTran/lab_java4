package com.thienloc.springboot.lab3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "fullname", nullable = false)
    private String fullname;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "admin")
    private Boolean admin = false;

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;
}
