package animal_world.real_animals;

import animal_world.Predator;

public class Wolf extends Predator {
    public Wolf(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos) {
        super("Волк", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }
}
