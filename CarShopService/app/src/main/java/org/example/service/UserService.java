package org.example.service;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;

import java.util.List;

/**
 * Service for user management.
 */
public interface UserService {
    public List<User> getAll();

    public List<UserDTO> getAllDTO(List<User> users);

    public User getById(int id);

    public User update(User user);

    public List<User> getSortedUsers(String paramsSort);

    public List<User> getFilteredUsers(String nameFilter, String params);
}
