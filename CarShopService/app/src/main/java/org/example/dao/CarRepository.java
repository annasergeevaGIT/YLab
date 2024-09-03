package org.example.dao;

import lombok.Data;
import org.example.domain.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to manage Entity {@link Car} extends interface {@link JpaRepository} which enables CRUD operations
 */
@Repository
public interface CarRepository extends JpaRepository<Car,Integer>{
    List<Car> findByBrand(String brand);

    List<Car> findByCondition(String condition);

    List<Car> findByPrice(double price);
}
