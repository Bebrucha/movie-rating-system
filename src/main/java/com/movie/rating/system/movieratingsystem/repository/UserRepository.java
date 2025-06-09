package com.movie.rating.system.movieratingsystem.repository;

import com.movie.rating.system.movieratingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
