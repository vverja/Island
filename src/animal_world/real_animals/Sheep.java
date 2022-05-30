package animal_world.real_animals;

import animal_world.Herbivore;


public class Sheep extends Herbivore {
    public Sheep(double weight,
                 int maxNumberPerCell,
                 int speed,
                 double foodForSatietyInKilos) {
        super("Овца", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC11";
    }
}
