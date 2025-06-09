package com.movie.rating.system.movieratingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Movie entity representing a movie in the system")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the movie", example = "1")
    private Long id;

    @Schema(description = "Title of the movie", example = "The Shawshank Redemption")
    private String title;

    @Schema(description = "Description of the movie",
            example = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
    private String description;

    @Schema(description = "Year the movie was released", example = "1994")
    private int releaseYear;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Schema(description = "List of ratings for this movie")
    private List<Rating> ratings;
}
