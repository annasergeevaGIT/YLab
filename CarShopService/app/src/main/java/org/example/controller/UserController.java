package org.example.controller;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling user management actions.
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = service.getAll();
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<UserDTO>> getAllFiltered(@PathVariable String name_filter, @PathVariable String params) {
        List<User> users = service.getFilteredUsers(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/sort/{params}")
    public ResponseEntity<List<UserDTO>> getAllSorted(@PathVariable String params) {
        List<User> users = service.getSortedUsers(params);
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable int id) {
        User user = service.getById(id);
        return ResponseEntity.ok(UserMapper.INSTANCE.toDTO(user));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable int id) {
        User user = service.getById(id);
        User updateUser = UserMapper.INSTANCE.toEntity(userDTO);
        updateUser.setId(id);
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        User updatedUser = service.update(updateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.INSTANCE.toDTO(updatedUser));
    }
}