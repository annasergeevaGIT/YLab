package org.example.mapper;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
/**
 * Interface for mapping to DTO and back
 */
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toDTOList(List<User> users);
}