package com.movie.rating.system.movieratingsystem.integration;

import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.repository.RatingRepository;
import com.movie.rating.system.movieratingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private Movie movie;
    private User user;

    @BeforeEach
    void setUp() {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();

        movie = Movie.builder().title("Test Movie").description("Test Description").releaseYear(2023).build();

        user = User.builder().username("testuser").email("test@example.com").build();
    }

    @Test
    void saveAndFindMovie() {
        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(savedMovie.getId());

        Optional<Movie> foundMovie = movieRepository.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals("Test Movie", foundMovie.get().getTitle());
    }

    @Test
    void saveAndFindUser() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void saveRatingWithRelationships() {
        Movie savedMovie = movieRepository.save(movie);
        User savedUser = userRepository.save(user);

        Rating rating = Rating.builder().movie(savedMovie).user(savedUser).score(8).review("Great movie!").build();

        Rating savedRating = ratingRepository.save(rating);
        assertNotNull(savedRating.getId());

        Optional<Rating> foundRating = ratingRepository.findById(savedRating.getId());
        assertTrue(foundRating.isPresent());
        assertEquals(savedMovie.getId(), foundRating.get().getMovie().getId());
        assertEquals(savedUser.getId(), foundRating.get().getUser().getId());
        assertEquals(8, foundRating.get().getScore());
    }

    @Test
    void findAllMovies() {
        Movie movie1 = Movie.builder().title("Movie 1").description("Desc 1").releaseYear(2020).build();
        Movie movie2 = Movie.builder().title("Movie 2").description("Desc 2").releaseYear(2021).build();

        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> movies = movieRepository.findAll();
        assertEquals(2, movies.size());
    }

    @Test
    void findAllUsers() {
        User user1 = User.builder().username("user1").email("user1@test.com").build();
        User user2 = User.builder().username("user2").email("user2@test.com").build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }
}