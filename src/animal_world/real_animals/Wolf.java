package animal_world.real_animals;

import animal_world.Predator;
import game.Cell;

public class Wolf extends Predator {
    public Wolf(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos,
                Cell cell) {
        super("Волк", weight, maxNumberPerCell, speed, foodForSatietyInKilos, cell);
    }

    @Override
    public String toString() {
        return "\\uD83D\\uDC3A";
    }
}
