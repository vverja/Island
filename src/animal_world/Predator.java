package animal_world;

import game.Cell;
import game.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal{
    public Predator(String name, double weight,
                    int maxNumberPerCell,
                    int speed,
                    double foodForSatietyInKilos) {
        super(name, weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public void eat(Cell cell) {
        if (satietyPercent == 100)
            return;
        Map<String, Integer> probabilityMap = Game.getProbability().get(getName());
        CopyOnWriteArrayList<Animal> animals = cell.getAnimals();
        Optional<Animal> anyHerbivore = animals.stream()
                .filter(animal -> animal instanceof Herbivore)
                .filter(animal -> probabilityMap.get(animal.getName()) != 0)
                .findAny();

        anyHerbivore.ifPresent(
                herbivore->{
                    int nextInt = ThreadLocalRandom.current().nextInt(1, 101);
                    if (nextInt<=probabilityMap.get(herbivore.getName())){
                        double herbivoreWeight = herbivore.getWeight();
                        double satietyInKilos = (satietyPercent * getFoodForSatietyInKilos())/100;
                        double totalSatiety = satietyInKilos + herbivoreWeight;
                        if (totalSatiety>getFoodForSatietyInKilos()){
                            satietyPercent = 100;
                        }else{
                            satietyPercent = (totalSatiety/getFoodForSatietyInKilos()) * 100;
                        }
                        Game.getStatistics().death(herbivore.getName(), 1L);
                        animals.remove(herbivore);
                    }
                });

    }
}
