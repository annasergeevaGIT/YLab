package org.example.dao;

import jakarta.validation.constraints.PastOrPresent;
import org.example.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository to manage order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
    List<Order> findByDate(@PastOrPresent(message = "Year should not be in the future") LocalDate date);

    List<Order> findByStatus(String status);
}