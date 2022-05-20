package animal_world;

import game.Cell;

public abstract class Predator extends Animal{
    public Predator(String name, double weight,
                    int maxNumberPerCell,
                    int speed,
                    double foodForSatietyInKilos,
                    Cell cell) {
        super(name, weight, maxNumberPerCell, speed, foodForSatietyInKilos, cell);
    }

    @Override
    public void eat() {
        //todo Написать логику питания хищника
    }
}
