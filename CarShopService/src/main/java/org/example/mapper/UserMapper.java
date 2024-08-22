package org.example.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    static UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
}