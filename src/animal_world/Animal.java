package animal_world;

import game.Cell;

public abstract class Animal extends Unit{
    private final int speed;
    private final double foodForSatietyInKilos;
    private Cell cell;

    public Animal(String name, double weight, int maxNumberPerCell,
                  int speed,
                  double foodForSatietyInKilos,
                  Cell cell) {
        super(name, weight, maxNumberPerCell);
        this.speed = speed;
        this.foodForSatietyInKilos = foodForSatietyInKilos;

        this.cell = cell;
    }

    public abstract void eat();
    public void move(){
        //todo Написать логику движения животных
    }


    @Override
    public void toBreed() {
        //todo Написать логику размножения животных
    }


}
