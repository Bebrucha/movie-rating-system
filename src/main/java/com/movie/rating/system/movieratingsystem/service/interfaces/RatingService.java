package com.movie.rating.system.movieratingsystem.service.interfaces;

import com.movie.rating.system.movieratingsystem.dto.RatingDTO;
import com.movie.rating.system.movieratingsystem.model.Rating;

public interface RatingService {
    Rating addRating(RatingDTO ratingDTO);
}
