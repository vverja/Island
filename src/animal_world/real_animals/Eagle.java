package animal_world.real_animals;

import animal_world.Predator;


public class Eagle extends Predator {
    public Eagle(double weight,
                 int maxNumberPerCell,
                 int speed,
                 double foodForSatietyInKilos) {
        super("Орел", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83E\uDD85";
    }
}
