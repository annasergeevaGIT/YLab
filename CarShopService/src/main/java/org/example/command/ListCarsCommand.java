package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.CarController;

@AllArgsConstructor
public class ListCarsCommand implements Command {
    private CarController carController;
    @Override
    public void execute() {
        carController.listCars();
    }
}
