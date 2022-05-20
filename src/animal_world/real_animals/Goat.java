package animal_world.real_animals;

import animal_world.Herbivore;
import game.Cell;


public class Goat extends Herbivore {
    public Goat(String name, double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos,
                Cell cell) {
        super(name, weight, maxNumberPerCell, speed, foodForSatietyInKilos, cell);
    }

    @Override
    public String toString() {
        return "\\uD83D\\uDC10";
    }
}
