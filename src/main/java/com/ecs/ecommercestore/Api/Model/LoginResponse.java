package com.ecs.ecommercestore.Api.Model;

import lombok.Data;

/**
 * The response object sent from login request.
 * @author mohamednicer
 */
@Data
public class LoginResponse {

    /** The JWT token to be used for authentication. */
    private String jwt;

    /** Was the login process successful? */
    private boolean success;

    /** The reason for failure on login. */
    private String failureReason;
}
