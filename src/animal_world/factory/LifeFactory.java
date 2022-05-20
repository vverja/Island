package animal_world.factory;

import animal_world.Unit;
import game.Cell;

public interface LifeFactory {
    Unit createUnit(Cell cell);
}
