package com.ecs.ecommercestore.Api.Model;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * The information required to register a user.
 * @author mohamednicer
 */
@Data
public class RegistrationBody {

    /** The username. */
    @NotNull
    @NotBlank
    @Size(min=6, max=255)
    private String username;

    /** The email. */
    @NotNull
    @NotBlank
    @Email
    private String email;

    /** The password. */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(max=32)
    private String password;

    /** The first name. */
    @NotNull
    @NotBlank
    private String firstName;

    /** The last name. */
    @NotNull
    @NotBlank
    private String lastName;
}
