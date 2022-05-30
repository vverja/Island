package animal_world.real_animals;

import animal_world.Herbivore;


public class Boar extends Herbivore {
    public Boar(double weight,
                int maxNumberPerCell,
                int speed,
                double foodForSatietyInKilos) {
        super("Кабан", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\ud800\udc8b";
    }
}
