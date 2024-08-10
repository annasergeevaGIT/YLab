package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.UserController;

@AllArgsConstructor
public class AddUserCommand implements Command {
    private UserController userController;
    @Override
    public void execute() {
        userController.addUser();
    }
}
