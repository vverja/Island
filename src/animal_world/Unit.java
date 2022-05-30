package animal_world;

import game.Cell;

public abstract class Unit {
    private final String name;
    private final double weight;
    private final int maxNumberPerCell;

    public Unit(String name, double weight, int maxNumberPerCell) {
        this.name = name;
        this.weight = weight;
        this.maxNumberPerCell = maxNumberPerCell;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxNumberPerCell() {
        return maxNumberPerCell;
    }

    public String getName() {
        return name;
    }


    public abstract void toBreed(Cell cell);
}
