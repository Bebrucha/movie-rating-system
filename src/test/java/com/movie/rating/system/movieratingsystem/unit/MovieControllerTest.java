package com.movie.rating.system.movieratingsystem.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.rating.system.movieratingsystem.controller.MovieController;
import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.service.interfaces.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private MovieDTO movieDTO;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO();
        movieDTO.setTitle("Test Movie");
        movieDTO.setDescription("Test Description");
        movieDTO.setReleaseYear(2023);

        movie = Movie.builder().id(1L).title("Test Movie").description("Test Description").releaseYear(2023).build();
    }

    @Test
    void addMovieSuccess() throws Exception {
        when(movieService.createMovie(any(MovieDTO.class))).thenReturn(movie);

        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movieDTO))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.title").value("Test Movie")).andExpect(jsonPath("$.description").value("Test Description")).andExpect(jsonPath("$.releaseYear").value(2023));
    }

    @Test
    void getAllMoviesSuccess() throws Exception {
        List<Movie> movies = Collections.singletonList(movie);
        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0].id").value(1L)).andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    void getAverageRatingSuccess() throws Exception {
        when(movieService.getAverageRating(1L)).thenReturn(7.5);

        mockMvc.perform(get("/movies/1/average-rating")).andExpect(status().isOk()).andExpect(content().string("7.5"));
    }
}