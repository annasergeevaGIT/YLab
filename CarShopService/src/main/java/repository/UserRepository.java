package repository;

import java.util.ArrayList;
import model.User;
import java.util.*;

public class UserRepository {
    private  List<User> users = new ArrayList<>();
    private  int nextID = 1;

    public User findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    public void save(User user) {
        users.add(user);
    }

    public void update(User user) {
        User existingUser = findById(user.getId());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public boolean deleteById(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public int getNextId(){
        return nextID++;
    }
}
