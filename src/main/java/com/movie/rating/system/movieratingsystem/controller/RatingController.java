package com.movie.rating.system.movieratingsystem.controller;

import com.movie.rating.system.movieratingsystem.dto.RatingDTO;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.service.interfaces.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Tag(name = "Ratings", description = "Movie rating operations")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @Operation(summary = "Add a new rating",
            description = "Creates a new rating for a movie by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Rating created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rating.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid rating data (score not between 1-10, invalid movie/user ID)",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Movie or User not found",
                    content = @Content)
    })
    public ResponseEntity<Rating> addRating(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Rating data to be created",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RatingDTO.class)))
            @RequestBody RatingDTO ratingDTO) {
        Rating rating = ratingService.addRating(ratingDTO);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
}
