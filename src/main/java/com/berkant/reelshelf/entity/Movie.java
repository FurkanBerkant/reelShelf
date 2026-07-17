package com.berkant.reelshelf.entity;

import com.berkant.reelshelf.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie extends BaseEntity {
    private String name;
    private Integer year;
    private String genre;
    private String description;
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMovie> userMovies = new HashSet<>();
}
