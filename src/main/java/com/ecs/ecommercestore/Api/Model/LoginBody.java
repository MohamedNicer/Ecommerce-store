package com.ecs.ecommercestore.Api.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The body for the login requests.
 * @author mohamednicer
 */
@Data
public class LoginBody {

    /** The username to log in with. */
    @NotNull
    @NotBlank
    private String username;

    /** The password to log in with. */
    @NotNull
    @NotBlank
    private String password;
}
