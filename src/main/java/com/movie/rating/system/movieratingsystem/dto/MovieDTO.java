package com.movie.rating.system.movieratingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for creating/updating movies", requiredProperties = {"title"})
public class MovieDTO {

    @Schema(description = "Title of the movie", example = "The Shawshank Redemption")
    private String title;

    @Schema(description = "Description of the movie", example = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
    private String description;

    @Schema(description = "Year the movie was released", example = "1994", minimum = "1900", maximum = "2030")
    private int releaseYear;
}
