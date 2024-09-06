package org.example.service.jpa;

import lombok.SneakyThrows;
import org.example.dao.UserRepository;
import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceJpa implements UserService {

    private final UserRepository repository;

    public UserServiceJpa(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<UserDTO> getAllDTO(List<User> users) {
        return users.stream()
                .map(UserMapper.INSTANCE::toDTO)
                .toList();
    }
    @SneakyThrows
    @Override
    public User getById(int id) {
        final Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }
    @SneakyThrows
    @Override
    public List<User> getSortedUsers(String paramsSort) {
        return switch (paramsSort) {
            case "name" -> repository.getSortByName();
            case "age" -> repository.getSortByAge();
            case "city" -> repository.getSortByCity();
            default -> throw new ChangeSetPersister.NotFoundException();
        };
    }
    @SneakyThrows
    @Override
    public List<User> getFilteredUsers(String nameFilter, String params) {
        return switch (nameFilter) {
            case "name" -> repository.getByName(params);
            case "age" -> repository.getByAge(Integer.parseInt(params));
            case "city" -> repository.getByCity(params);
            default -> throw new ChangeSetPersister.NotFoundException();
        };
    }
}
