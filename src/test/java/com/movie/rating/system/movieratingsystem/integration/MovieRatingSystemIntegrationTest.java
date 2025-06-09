package com.movie.rating.system.movieratingsystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.rating.system.movieratingsystem.dto.MovieDTO;
import com.movie.rating.system.movieratingsystem.dto.UserDTO;
import com.movie.rating.system.movieratingsystem.model.Movie;
import com.movie.rating.system.movieratingsystem.model.User;
import com.movie.rating.system.movieratingsystem.repository.MovieRepository;
import com.movie.rating.system.movieratingsystem.repository.RatingRepository;
import com.movie.rating.system.movieratingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class MovieRatingSystemIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllMoviesWithMultipleMovies() throws Exception {
        String[] titles = {"Movie 1", "Movie 2", "Movie 3"};
        int[] years = {2020, 2021, 2022};

        for (int i = 0; i < 3; i++) {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(titles[i]);
            movieDTO.setDescription("Description for " + titles[i]);
            movieDTO.setReleaseYear(years[i]);

            mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movieDTO))).andExpect(status().isCreated());
        }

        mockMvc.perform(get("/movies")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[*].title", containsInAnyOrder("Movie 1", "Movie 2", "Movie 3"))).andExpect(jsonPath("$[*].releaseYear", containsInAnyOrder(2020, 2021, 2022)));
    }

    @Test
    void getAllUsersWithMultipleUsers() throws Exception {
        String[] usernames = {"alice", "bob", "charlie"};
        String[] emails = {"alice@test.com", "bob@test.com", "charlie@test.com"};

        for (int i = 0; i < 3; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(usernames[i]);
            userDTO.setEmail(emails[i]);

            mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isCreated());
        }

        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[*].username", containsInAnyOrder("alice", "bob", "charlie"))).andExpect(jsonPath("$[*].email", containsInAnyOrder("alice@test.com", "bob@test.com", "charlie@test.com")));
    }

    @Test
    void getUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("specificuser");
        userDTO.setEmail("specific@test.com");

        String userResponse = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(userResponse, User.class);

        mockMvc.perform(get("/users/" + user.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(user.getId())).andExpect(jsonPath("$.username").value("specificuser")).andExpect(jsonPath("$.email").value("specific@test.com"));
    }

    @Test
    void movieAverageRatingNoRatings() throws Exception {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Unrated Movie");
        movieDTO.setDescription("A movie with no ratings");
        movieDTO.setReleaseYear(2023);

        String movieResponse = mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movieDTO))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Movie movie = objectMapper.readValue(movieResponse, Movie.class);

        mockMvc.perform(get("/movies/" + movie.getId() + "/average-rating")).andExpect(status().isOk()).andExpect(content().string("0.0"));
    }
}