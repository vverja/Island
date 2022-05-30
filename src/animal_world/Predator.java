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
        //если сытость 100% не едим
        if (satietyPercent == 100)
            return;
        //получам мапу вероятностей
        Map<String, Integer> probabilityMap = Game.getProbability().get(getName());

        CopyOnWriteArrayList<Animal> animals = cell.getAnimals();
        //находим в списке животных - травоядных и вероятность поедания которых не равна 0
        Optional<Animal> anyHerbivore = animals.stream()
                .filter(animal -> animal instanceof Herbivore)
                .filter(animal -> probabilityMap.get(animal.getName()) != 0)
                .findAny();
        //если нашлось травоядное, поедаем - удаляем из списка
        anyHerbivore.ifPresent(
                herbivore->{
                    int nextInt = ThreadLocalRandom.current().nextInt(1, 101);
                    if (nextInt<=probabilityMap.get(herbivore.getName())){
                        double herbivoreWeight = herbivore.getWeight();
                        double satietyInKilos = (satietyPercent * getFoodForSatietyInKilos())/100;
                        double totalSatiety = satietyInKilos + herbivoreWeight;
                        //процес поедания увеличивает сытость
                        if (totalSatiety>getFoodForSatietyInKilos()){
                            satietyPercent = 100;
                        }else{
                            satietyPercent = (totalSatiety/getFoodForSatietyInKilos()) * 100;
                        }
                        //регистрируем в классе статистика смерть
                        Game.getStatistics().death(herbivore.getName(), 1L);
                        animals.remove(herbivore);
                    }
                });

    }
}
