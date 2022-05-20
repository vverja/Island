package animal_world.factory;

import animal_world.Plants;
import animal_world.Unit;
import game.Cell;

import java.util.ArrayList;

public class PlantsFactory implements LifeFactory{
    @Override
    public Unit createUnit(Cell cell) {
        double weight = 1;
        return new Plants(weight,1000, cell);
    }
}
