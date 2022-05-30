package animal_world.real_animals;

import animal_world.Herbivore;


public class Duck extends Herbivore {
    public Duck(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos) {
        super("Утка", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC14";
    }
}
