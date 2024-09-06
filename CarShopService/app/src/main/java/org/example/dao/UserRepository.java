package org.example.dao;

import org.example.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to manage user entity
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> getByAge(int age);

    List<User> getByName(String name);

    List<User> getByCity(String city);

    @Query("SELECT u FROM User u ORDER BY u.username ASC")
    List<User> getSortByName();

    @Query("SELECT u FROM User u ORDER BY u.age ASC")
    List<User> getSortByAge();

    @Query("SELECT u FROM User u ORDER BY u.email ASC")
    List<User> getSortByCity();
}
