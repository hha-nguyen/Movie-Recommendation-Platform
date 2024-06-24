package com.mesto.movieplatform.dtoes;

import lombok.*;

import java.time.LocalDateTime;

import com.amazonaws.services.appstream.model.User;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDTO {
    private String content;
    private LocalDateTime commentAt;
    private Integer userId;
    private Integer movieId;
}
