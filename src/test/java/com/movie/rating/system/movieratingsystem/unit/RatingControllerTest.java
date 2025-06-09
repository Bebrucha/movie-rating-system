package com.movie.rating.system.movieratingsystem.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.rating.system.movieratingsystem.controller.RatingController;
import com.movie.rating.system.movieratingsystem.dto.RatingDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.service.interfaces.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    private RatingDTO ratingDTO;
    private Rating rating;

    @BeforeEach
    void setUp() {
        ratingDTO = new RatingDTO();
        ratingDTO.setMovieId(1L);
        ratingDTO.setUserId(1L);
        ratingDTO.setScore(8);
        ratingDTO.setReview("Great movie!");

        Movie movie = Movie.builder().id(1L).title("Test Movie").build();
        User user = User.builder().id(1L).username("testuser").build();

        rating = Rating.builder().id(1L).movie(movie).user(user).score(8).review("Great movie!").build();
    }

    @Test
    void addRatingSuccess() throws Exception {
        when(ratingService.addRating(any(RatingDTO.class))).thenReturn(rating);

        mockMvc.perform(post("/ratings").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ratingDTO))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.score").value(8)).andExpect(jsonPath("$.review").value("Great movie!")).andExpect(jsonPath("$.movie.id").value(1L)).andExpect(jsonPath("$.user.id").value(1L));
    }
}
