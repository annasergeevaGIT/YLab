package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.SearchController;
import org.example.in.UserController;

@AllArgsConstructor
public class SearchOrdersCommand implements Command {
    private SearchController searchController;
    @Override
    public void execute() {
        searchController.searchOrders();
    }
}
