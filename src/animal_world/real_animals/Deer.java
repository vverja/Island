package animal_world.real_animals;

import animal_world.Herbivore;


public class Deer extends Herbivore {
    public Deer(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos) {
        super("Олень", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83E\uDD8C";
    }
}
