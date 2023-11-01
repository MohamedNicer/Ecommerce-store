package com.ecs.ecommercestore.Api.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request body to reset a password using a password reset token.
 * @author mohamednicer
 */
@Data
public class PasswordResetBody {

    /** The token to authenticate the request. */
    @NotNull
    @NotBlank
    private String token;

    /** The password to set to the account. */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(max = 32)
    private String password;
}
