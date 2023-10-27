package com.ecs.ecommercestore.Api.Auth;

import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    UserService userService;

    public AuthenticationController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
