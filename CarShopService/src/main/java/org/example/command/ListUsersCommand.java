package org.example.command;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.in.UserController;
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersCommand implements Command {
    private UserController userController;
    @Override
    public void execute() {
        userController.listUsers();
    }
}
