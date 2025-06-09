package com.movie.rating.system.movieratingsystem.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.rating.system.movieratingsystem.controller.UserController;
import com.movie.rating.system.movieratingsystem.dto.UserDTO;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");

        user = User.builder().id(1L).username("testuser").email("test@example.com").build();
    }

    @Test
    void addUserSuccess() throws Exception {
        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.username").value("testuser")).andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getAllUsersSuccess() throws Exception {
        List<User> users = Collections.singletonList(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0].id").value(1L)).andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    void getUserSuccess() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.username").value("testuser")).andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
