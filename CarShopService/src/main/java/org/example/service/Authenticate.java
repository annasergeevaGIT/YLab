package org.example.service;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;

/**
 * Interface for authentication and authorization
 */
public interface Authenticate {

    boolean register(UserDTO userDTO);
    UserDTO login(String username, String password);

}
