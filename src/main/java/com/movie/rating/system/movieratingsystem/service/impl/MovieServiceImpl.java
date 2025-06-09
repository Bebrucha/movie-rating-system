package com.movie.rating.system.movieratingsystem.service.impl;

import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.Rating;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.service.interfaces.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(MovieDTO dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .releaseYear(dto.getReleaseYear())
                .build();
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public double getAverageRating(Long movieId) {
        Movie movie = getMovieById(movieId);
        if (movie.getRatings() == null || movie.getRatings().isEmpty()) {
            return 0.0;
        }
        return movie.getRatings().stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
