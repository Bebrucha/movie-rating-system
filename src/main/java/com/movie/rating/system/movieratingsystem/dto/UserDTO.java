package com.movie.rating.system.movieratingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        description = "Data Transfer Object for creating/updating users",
        requiredProperties = { "username", "email" }
)
public class UserDTO {

    @Schema(
            description = "Username of the user",
            example = "moviefan123"
    )
    private String username;

    @Schema(
            description = "Email address of the user",
            example = "user@example.com",
            format = "email"
    )
    private String email;
}
