package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.OrderController;

@AllArgsConstructor
public class CancelOrderCommand implements Command {
    private OrderController orderController;
    @Override
    public void execute() {
        orderController.cancelOrder();
    }
}
