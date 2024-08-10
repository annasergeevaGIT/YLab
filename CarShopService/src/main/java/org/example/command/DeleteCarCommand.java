package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.CarController;

@AllArgsConstructor
public class DeleteCarCommand implements Command {
    private CarController carController;
    @Override
    public void execute() {
        carController.deleteCar();
    }
}
