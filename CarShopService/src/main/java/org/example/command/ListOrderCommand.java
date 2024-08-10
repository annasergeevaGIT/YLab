package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.OrderController;

@AllArgsConstructor
public class ListOrderCommand implements Command {
    private OrderController orderController;
    @Override
    public void execute() {
        orderController.listOrders();
    }
}
