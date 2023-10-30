package com.ecs.ecommercestore.Api.Model;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private boolean success;
    private String failureReason;
}
