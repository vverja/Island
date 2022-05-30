package animal_world;


import animal_world.factory.PlantsFactory;
import game.Cell;
import game.Game;

import java.util.concurrent.CopyOnWriteArrayList;

public class Plants extends Unit{

    public Plants(double weight, int maxNumberPerCell) {
        super("Растение", weight, maxNumberPerCell);
    }

    @Override
    public void toBreed(Cell cell) {
        CopyOnWriteArrayList<Plants> plants = cell.getPlants();
        if (plants.size()<getMaxNumberPerCell())
            plants.add(new PlantsFactory().createUnit("Растения", Game.getGameProperties()));
    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F";
    }
}
