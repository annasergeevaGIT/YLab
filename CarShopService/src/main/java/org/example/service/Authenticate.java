package org.example.service;

import org.example.domain.dto.UserDTO;

/**
 * Interface for authentication and authorization
 */
public interface Authenticate {

    boolean register(UserDTO userDTO);
    UserDTO login(String username, String password);

}
