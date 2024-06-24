package com.mesto.movieplatform.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String status;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToOne
    @JoinColumn(name = "host_id")
    private User host;

    @OneToMany(mappedBy = "room")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
