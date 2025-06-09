package com.movie.rating.system.movieratingsystem.unit;

import com.movie.rating.system.movieratingsystem.dto.RatingDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.repository.RatingRepository;
import com.movie.rating.system.movieratingsystem.repository.UserRepository;
import com.movie.rating.system.movieratingsystem.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private RatingDTO ratingDTO;
    private Movie movie;
    private User user;
    private Rating rating;

    @BeforeEach
    void setUp() {
        ratingDTO = new RatingDTO();
        ratingDTO.setMovieId(1L);
        ratingDTO.setUserId(1L);
        ratingDTO.setScore(8);
        ratingDTO.setReview("Great movie!");

        movie = Movie.builder().id(1L).title("Test Movie").build();

        user = User.builder().id(1L).username("testuser").build();

        rating = Rating.builder().id(1L).movie(movie).user(user).score(8).review("Great movie!").build();
    }

    @Test
    void addRatingSuccess() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.addRating(ratingDTO);

        assertNotNull(result);
        assertEquals(ratingDTO.getScore(), result.getScore());
        assertEquals(ratingDTO.getReview(), result.getReview());
        assertEquals(movie, result.getMovie());
        assertEquals(user, result.getUser());
        verify(movieRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void addRatingMovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.addRating(ratingDTO));
        assertEquals("Movie not found", exception.getMessage());
        verify(movieRepository).findById(1L);
        verify(userRepository, never()).findById(any());
        verify(ratingRepository, never()).save(any());
    }

    @Test
    void addRatingUserNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.addRating(ratingDTO));
        assertEquals("User not found", exception.getMessage());
        verify(movieRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(ratingRepository, never()).save(any());
    }

    @Test
    void addRatingScoreTooLow() {
        ratingDTO.setScore(0);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(ratingDTO));
        assertEquals("Score must be between 1 and 10", exception.getMessage());
        verify(ratingRepository, never()).save(any());
    }

    @Test
    void addRatingScoreTooHigh() {
        ratingDTO.setScore(11);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(ratingDTO));
        assertEquals("Score must be between 1 and 10", exception.getMessage());
        verify(ratingRepository, never()).save(any());
    }
}