package com.ecs.ecommercestore.Api.Model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationBody {
    @NotNull
    @NotBlank
    @Size(min = 6,max = 255)
    private String username;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=,*[A-Za-z])(?=,*\\d)[A-Za=z\\d]{6,}$")
    @Size(max = 32)
    private String password;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
}
