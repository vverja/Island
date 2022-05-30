package animal_world.real_animals;

import animal_world.Herbivore;


public class Buffalo extends Herbivore {
    public Buffalo(double weight,
                   int maxNumberPerCell,
                   int speed,
                   double foodForSatietyInKilos
                   ) {
        super("Буйвол", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\ud83d\udc03";
    }
}
