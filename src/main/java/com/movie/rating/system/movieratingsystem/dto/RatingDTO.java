package com.movie.rating.system.movieratingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        description = "Data Transfer Object for creating movie ratings",
        requiredProperties = { "movieId", "userId", "score" }
)
public class RatingDTO {

    @Schema(description = "ID of the movie being rated", example = "1")
    private Long movieId;

    @Schema(description = "ID of the user giving the rating", example = "1")
    private Long userId;

    @Schema(
            description = "Rating score (1-10)",
            example = "8",
            minimum = "1",
            maximum = "10"
    )
    private int score;

    @Schema(
            description = "Written review of the movie",
            example = "An excellent movie with great character development and storytelling."
    )
    private String review;
}
