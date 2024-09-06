package org.example.mapper;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * Interface for mapping to DTO and back
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    List<UserDTO> toDTOList(List<User> users);
}