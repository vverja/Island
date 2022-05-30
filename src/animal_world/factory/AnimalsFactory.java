package animal_world.factory;

import animal_world.Animal;
import animal_world.real_animals.*;
import game.Characteristics;
import animal_world.real_animals.Eagle;
import game.GameProperties;

import java.util.concurrent.ThreadLocalRandom;

public class AnimalsFactory implements LifeFactory{
    @Override
    public Animal createUnit(String name, GameProperties properties) {
        Characteristics characteristics = properties.getCharacteristicsByName(name);
        double weight = characteristics.getWeight();
        int maxNumPerCell = characteristics.getMaxNumberPerCell();
        int speed = characteristics.getSpeed();
        double foodForSatietyInKilos = characteristics.getFoodForSatietyInKilos();
        return switch (name) {
            case "Волк" -> new Wolf(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Удав" -> new Boa(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Лиса" -> new Fox(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Медведь" -> new Bear(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Орел" -> new Eagle(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Лошадь" -> new Horse(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Олень" -> new Deer(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Кролик" -> new Rabbit(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Мышь" -> new Mouse(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Коза" -> new Goat(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Овца" -> new Sheep(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Кабан" -> new Boar(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Буйвол" -> new Buffalo(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Утка" -> new Duck(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);
            case "Гусеница" -> new Caterpillar(weight,
                    maxNumPerCell,
                    speed,
                    foodForSatietyInKilos);

            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
