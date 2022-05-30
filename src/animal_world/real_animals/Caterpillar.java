package animal_world.real_animals;

import animal_world.Herbivore;


public class Caterpillar extends Herbivore {
    public Caterpillar(double weight,
                       int maxNumberPerCell,
                       int speed,
                       double foodForSatietyInKilos) {
        super("Гусеница", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC1B";
    }
}
