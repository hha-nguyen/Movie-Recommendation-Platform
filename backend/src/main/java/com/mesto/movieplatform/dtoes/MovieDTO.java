package com.mesto.movieplatform.dtoes;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.jni.Local;

import com.mesto.movieplatform.entities.Rating;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private Integer id;
    private String title;
    private String overview;
    private String posterPath;
    private String streamingLink;
    private Integer voteCount;
    private Double voteAverage;
    private List<GenreDTO> genres;
    private List<CommentDTO> comments;
    private LocalDateTime movieSaveDate;
}
