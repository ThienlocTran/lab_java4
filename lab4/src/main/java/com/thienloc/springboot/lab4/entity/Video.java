package com.thienloc.springboot.lab4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "poster")
    private String poster;
    
    @Column(name = "views")
    private Integer views = 0;
    
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;
    
    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "video")
    private List<Favorite> favorites;
    
    @OneToMany(mappedBy = "video")
    private List<Share> shares;
    
    // Display fields (not mapped to database)
    @Transient
    private String thumbnailUrl;
    
    @Transient
    private String shortViews;
    
    @Transient
    private String duration;
    
    @Transient
    private String timeAgo;
}
