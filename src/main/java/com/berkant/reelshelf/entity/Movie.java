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

    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Long tmdbId;

    @Column(name = "title", nullable = false)
    private String title;

    private Integer releaseYear;

    @Column(length = 2000)
    private String overview;

    private String posterPath;

    private String originalLanguage;

    private Double voteAverage;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserMovie> userMovies = new HashSet<>();

}
