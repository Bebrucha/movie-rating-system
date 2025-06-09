package com.movie.rating.system.movieratingsystem.repository;

import com.movie.rating.system.movieratingsystem.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
