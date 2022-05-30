package animal_world;

import game.Cell;
import game.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal{
    public Herbivore(String name, double weight,
                     int maxNumberPerCell,
                     int speed,
                     double foodForSatietyInKilos
                     ) {
        super(name, weight, maxNumberPerCell, speed, foodForSatietyInKilos);
    }

    @Override
    public void eat(Cell cell) {
        if (satietyPercent==100) {
            return;
        }
        CopyOnWriteArrayList<Plants> plants = cell.getPlants();
        if (plants.size()==0) {
            return;
        }

        Map<String, Map<String, Integer>> probabilityMap = Game.getProbability(); //находим есть ли вероятность съесть какого-нибудб травоядного (гусеницу)
        Optional<Map.Entry<String, Integer>> eatableHerbivoreProbability = probabilityMap.get(getName()).entrySet().stream()
                                    .filter(el -> !"Растения".equals(el.getKey())&&el.getValue() > 0).findFirst();//ищем в мапе вероятность больше 0
        eatableHerbivoreProbability.ifPresentOrElse(probabilityEntry->{
            Optional<Animal> firstEatableHerbivore = cell.getAnimals().stream()
                    .filter(animal -> animal.getName().equals(probabilityEntry.getKey()))
                    .findFirst();//если вероятность есть находим по названию реальный инстанс животного
            firstEatableHerbivore.ifPresent(herbivore -> {
                int nextInt = ThreadLocalRandom.current().nextInt(1, 101);
                if (nextInt <= probabilityEntry.getValue()) {
                    double herbivoreWeight = herbivore.getWeight();
                    double satietyInKilos = (satietyPercent * getFoodForSatietyInKilos()) / 100;
                    double totalSatiety = satietyInKilos + herbivoreWeight;
                    if (totalSatiety > getFoodForSatietyInKilos()) {
                        satietyPercent = 100;
                    } else {
                        satietyPercent = (totalSatiety / getFoodForSatietyInKilos()) * 100;
                    }
                    Game.getStatistics().death(herbivore.getName(), 1L);
                    cell.getAnimals().remove(herbivore);
                }
            });
        },()->{//если вероятность схрумать травоядного нет - ежим растение
            Game.getStatistics().death("Растения", 1L);
            plants.remove(plants.size()-1);
            double satietyInKilos = (satietyPercent * getFoodForSatietyInKilos())/100;
            double totalSatiety = ++satietyInKilos;
            if (totalSatiety>getFoodForSatietyInKilos()){
                satietyPercent = 100;
            }else{
                satietyPercent = (totalSatiety/getFoodForSatietyInKilos()) * 100;
            }
        });
    }

}
