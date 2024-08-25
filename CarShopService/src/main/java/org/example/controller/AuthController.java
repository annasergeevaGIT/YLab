package org.example.controller;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.example.service.AuthServiceJdbc;
import org.example.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthServiceJdbc authService;
    private final UserMapper userMapper;

    public AuthController(AuthServiceJdbc authService) {
        this.authService = authService;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    /**
     * Registers a new user.
     *
     * @param userDTO The user data to register
     * @return Response entity with status
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (authService.register(userDTO)) {
            return new ResponseEntity<>("Register successful.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Username already in use.", HttpStatus.CONFLICT);
        }
    }

    /**
     * Authenticates a user.
     *
     * @param userDTO The login credentials
     * @return The authenticated user, or error if credentials are wrong
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
        User user = authService.login(userDTO.username(), userDTO.password());

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
