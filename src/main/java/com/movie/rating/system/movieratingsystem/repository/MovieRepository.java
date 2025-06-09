package com.movie.rating.system.movieratingsystem.repository;

import com.movie.rating.system.movieratingsystem.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
