package com.movie.rating.system.movieratingsystem.service.impl;

import com.movie.rating.system.movieratingsystem.dto.UserDTO;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.repository.UserRepository;
import com.movie.rating.system.movieratingsystem.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO dto) {
        User user = User.builder().username(dto.getUsername()).email(dto.getEmail()).build();
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
