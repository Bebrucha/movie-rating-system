package com.movie.rating.system.movieratingsystem.unit;

import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

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
    void createMovieSuccess() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.createMovie(movieDTO);

        assertNotNull(result);
        assertEquals(movieDTO.getTitle(), result.getTitle());
        assertEquals(movieDTO.getDescription(), result.getDescription());
        assertEquals(movieDTO.getReleaseYear(), result.getReleaseYear());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void getAllMoviesSuccess() {
        List<Movie> movies = Collections.singletonList(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(1, result.size());
        assertEquals(movie.getTitle(), result.getFirst().getTitle());
        verify(movieRepository).findAll();
    }

    @Test
    void getMovieByIdSuccess() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals(movie.getId(), result.getId());
        assertEquals(movie.getTitle(), result.getTitle());
        verify(movieRepository).findById(1L);
    }

    @Test
    void getMovieByIdNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> movieService.getMovieById(1L));
        assertEquals("Movie not found", exception.getMessage());
        verify(movieRepository).findById(1L);
    }

    @Test
    void getAverageRatingWithRatings() {
        Rating rating1 = Rating.builder().score(8).build();
        Rating rating2 = Rating.builder().score(6).build();
        movie.setRatings(Arrays.asList(rating1, rating2));

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        double result = movieService.getAverageRating(1L);

        assertEquals(7.0, result);
        verify(movieRepository).findById(1L);
    }

    @Test
    void getAverageRatingNoRatings() {
        movie.setRatings(Collections.emptyList());
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        double result = movieService.getAverageRating(1L);

        assertEquals(0.0, result);
        verify(movieRepository).findById(1L);
    }

    @Test
    void getAverageRatingNullRatings() {
        movie.setRatings(null);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        double result = movieService.getAverageRating(1L);

        assertEquals(0.0, result);
        verify(movieRepository).findById(1L);
    }
}
