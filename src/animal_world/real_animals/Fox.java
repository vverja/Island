package animal_world.real_animals;

import animal_world.Predator;


public class Fox extends Predator {
    public Fox(double weight,
               int maxNumberPerCell,
               int speed,
               double foodForSatietyInKilos) {
        super("Лиса", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83E\uDD8A";
    }
}
