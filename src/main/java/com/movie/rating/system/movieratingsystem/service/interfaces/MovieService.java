package com.movie.rating.system.movieratingsystem.service.interfaces;

import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;

import java.util.List;

public interface MovieService {
    Movie createMovie(MovieDTO movieDTO);

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    double getAverageRating(Long movieId);
}
