package animal_world.real_animals;

import animal_world.Predator;


public class Boa extends Predator {
    public Boa(double weight,
               int maxNumberPerCell,
               int speed,
               double foodForSatietyInKilos) {
        super("Удав", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC0D";
    }
}
