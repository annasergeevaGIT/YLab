package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.CarController;
import org.example.in.OrderController;

import java.security.cert.Extension;

@AllArgsConstructor
public class CreateOrderCommand implements Command {
    private OrderController orderController;
    @Override
    public void execute() {
        orderController.createOrder();
    }
}
