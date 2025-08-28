package com.e_commerce.bosch.services;

import com.e_commerce.bosch.dtos.AuthResponseDTO;
import com.e_commerce.bosch.dtos.UserLoginDTO;
import com.e_commerce.bosch.dtos.UserRegisterDTO;
import com.e_commerce.bosch.entities.Role;
import com.e_commerce.bosch.entities.User;
import com.e_commerce.bosch.exceptions.ApiExceptionFactory;
import com.e_commerce.bosch.repositories.UserRepository;
import com.e_commerce.bosch.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        if(userRepository.existsByUsername(userRegisterDTO.getUsername())){
           throw ApiExceptionFactory.usernameAlredyTaken();
        }

        String newPasswordHash = passwordEncoder.encode(userRegisterDTO.getPassword());

        userRepository.save(User.builder()
                        .username(userRegisterDTO.getUsername())
                        .passwordHash(newPasswordHash)
                        .role(Role.USER)
                        .fullName(userRegisterDTO.getFullName())
                        .build());
    }

    public AuthResponseDTO loginUser(UserLoginDTO userLoginDTO) {


        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(ApiExceptionFactory::userNotFound);

        String newToken = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        if(passwordNotMatch(userLoginDTO.getPassword(), user.getPasswordHash())){
            throw ApiExceptionFactory.invalidCredentials();
        }

        return AuthResponseDTO.builder()
                .token(newToken)
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    private boolean passwordNotMatch(String userPassword, String passwordHash) {
        return !passwordEncoder.matches(userPassword, passwordHash);
    }
}
