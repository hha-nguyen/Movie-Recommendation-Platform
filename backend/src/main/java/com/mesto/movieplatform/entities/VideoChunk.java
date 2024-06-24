package com.mesto.movieplatform.entities;

import lombok.*;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.function.DoublePredicate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "video_chunk")
public class VideoChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "encoding_type")
    private String encodingType;
    @Column(name = "resolution")
    private String resolution;
    @Column(name = "chunk_order")
    private Integer chunkOrder;
    @Column(name = "data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
