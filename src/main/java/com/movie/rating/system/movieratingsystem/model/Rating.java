package com.movie.rating.system.movieratingsystem.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Rating entity representing a user's rating of a movie")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the rating", example = "1")
    private Long id;

    @Schema(description = "Rating score from 1 to 10", example = "8", minimum = "1", maximum = "10")
    private int score;

    @Schema(description = "Written review accompanying the rating", example = "Excellent movie with great character development")
    private String review;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @Schema(description = "Movie being rated")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User who gave the rating")
    private User user;
}