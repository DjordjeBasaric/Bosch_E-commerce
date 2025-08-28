package com.e_commerce.bosch.controllers;

import com.e_commerce.bosch.dtos.AuthResponseDTO;
import com.e_commerce.bosch.dtos.ProductDTO;
import com.e_commerce.bosch.dtos.UserLoginDTO;
import com.e_commerce.bosch.dtos.UserRegisterDTO;
import com.e_commerce.bosch.services.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegisterDTO userRegisterDTO){
        userService.registerUser(userRegisterDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO){

        return ResponseEntity.ok(userService.loginUser(userLoginDTO));
    }
}
