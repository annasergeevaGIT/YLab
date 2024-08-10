package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.UserController;

@AllArgsConstructor
public class ChangeUserRoleCommand implements Command {
    private UserController userController;
    @Override
    public void execute() {
        userController.changeUserRole();
    }
}
