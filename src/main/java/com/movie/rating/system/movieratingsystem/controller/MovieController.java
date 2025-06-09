package com.movie.rating.system.movieratingsystem.controller;

import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.service.interfaces.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "Movie management operations")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @Operation(summary = "Add a new movie", description = "Creates a new movie in the system")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Movie created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))), @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)})
    public ResponseEntity<Movie> addMovie(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Movie data to be created", required = true, content = @Content(schema = @Schema(implementation = MovieDTO.class))) @RequestBody MovieDTO movieDTO) {
        Movie createdMovie = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieves a list of all movies in the system")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Movies retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Movie.class))), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}/average-rating")
    @Operation(summary = "Get average rating for a movie", description = "Calculates and returns the average rating for a specific movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Average rating calculated successfully", content = @Content(mediaType = "application/json", schema = @Schema(type = "number", format = "double"))), @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)})
    public ResponseEntity<Double> getAverageRating(@Parameter(description = "ID of the movie to get average rating for", required = true) @PathVariable Long id) {
        double avgRating = movieService.getAverageRating(id);
        return ResponseEntity.ok(avgRating);
    }
}