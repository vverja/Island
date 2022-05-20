package animal_world;

import game.Cell;

public abstract class Herbivore extends Animal{
    public Herbivore(String name, double weight,
                     int maxNumberPerCell,
                     int speed,
                     double foodForSatietyInKilos,
                     Cell cell) {
        super(name, weight, maxNumberPerCell, speed, foodForSatietyInKilos, cell);
    }

    @Override
    public void eat() {
        //todo Написать логику питания травоядного
    }
}
