package com.thienloc.springboot.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
