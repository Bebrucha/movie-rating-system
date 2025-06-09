package com.movie.rating.system.movieratingsystem.service.interfaces;

import com.movie.rating.system.movieratingsystem.dto.UserDTO;
import com.movie.rating.system.movieratingsystem.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);

    List<User> getAllUsers();

    User getUserById(Long id);
}
