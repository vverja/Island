package animal_world.real_animals;

import animal_world.Predator;


public class Bear extends Predator {
    public Bear(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos) {
        super("Медведь", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3B";
    }
}
