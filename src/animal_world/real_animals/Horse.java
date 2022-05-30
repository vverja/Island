package animal_world.real_animals;

import animal_world.Herbivore;


public class Horse extends Herbivore {
    public Horse(double weight,
                 int maxNumberPerCell,
                 int speed,
                 double foodForSatietyInKilos) {
        super("Лошадь", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC0E";
    }
}
