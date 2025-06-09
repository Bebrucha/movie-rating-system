package com.movie.rating.system.movieratingsystem.service.impl;

import com.movie.rating.system.movieratingsystem.dto.RatingDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.repository.RatingRepository;
import com.movie.rating.system.movieratingsystem.repository.UserRepository;
import com.movie.rating.system.movieratingsystem.service.interfaces.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Override
    public Rating addRating(RatingDTO dto) {
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getScore() < 1 || dto.getScore() > 10) {
            throw new IllegalArgumentException("Score must be between 1 and 10");
        }

        Rating rating = Rating.builder().movie(movie).user(user).score(dto.getScore()).review(dto.getReview()).build();

        return ratingRepository.save(rating);
    }
}
