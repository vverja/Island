package game;

import lombok.Getter;

import java.util.ArrayList;


public class Cell {
    @Getter
    private final int x, y;
    @Getter
    private final ArrayList<Direction> availableDirections;

    public Cell(int x, int y, ArrayList<Direction> availableDirections) {
        this.x = x;
        this.y = y;
        this.availableDirections = availableDirections;
    }
}
