package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private int id;
    private Car car;
    private User user;
    private OrderStatus status;
    private LocalDateTime createdAt;


    public OrderDTO(int i, OrderStatus status) {
        this.id = i;
        this.status = status;
    }
}
