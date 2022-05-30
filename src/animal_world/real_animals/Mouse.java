package animal_world.real_animals;

import animal_world.Herbivore;


public class Mouse extends Herbivore {
    public Mouse(double weight,
                 int maxNumberPerCell,
                 int speed,
                 double foodForSatietyInKilos) {
        super("Мышь", weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC39";
    }
}
