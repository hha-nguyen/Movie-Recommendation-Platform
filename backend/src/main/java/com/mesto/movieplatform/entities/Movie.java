package com.mesto.movieplatform.entities;

import lombok.*;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.function.DoublePredicate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "overview", columnDefinition = "TEXT", length = 2048)
    private String overview;
    @Column(name = "poster_path")
    private String posterPath;
    @Column(name = "streaming_link")
    private String streamingLink;
    @Column(name = "vote_count")
    private Integer voteCount;
    @Column(name = "vote_average")
    private Double voteAverage;
    @Column(name = "save_date")
    private LocalDateTime movieSaveDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<VideoChunk> videoChunks = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Recommendation> recommendations = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings = new ArrayList<>();
}
