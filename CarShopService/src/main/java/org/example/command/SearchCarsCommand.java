package org.example.command;

import lombok.AllArgsConstructor;
import org.example.in.SearchController;

@AllArgsConstructor
public class SearchCarsCommand implements Command {
    private SearchController searchController;
    @Override
    public void execute() {
        searchController.searchCars();
    }
}
