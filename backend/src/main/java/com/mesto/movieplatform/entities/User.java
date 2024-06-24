package com.mesto.movieplatform.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.engine.internal.JoinSequence.Join;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer age;
    @Column(name = "profile_pic_path")
    private String profileImage;
    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @OneToOne(mappedBy = "host")
    private Room hostedRoom;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @Nullable
    private Room room;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Recommendation recommendation;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings = new ArrayList<>();

    public User(String name, String email, String password, List<Role> roles) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
