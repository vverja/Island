package animal_world.factory;

import animal_world.Plants;
import game.Characteristics;
import game.GameProperties;

public class PlantsFactory implements LifeFactory{
    @Override
    public Plants createUnit(String name, GameProperties properties) {
        Characteristics plantCharacteristic = properties.getCharacteristicsByName(name);
        return new Plants(plantCharacteristic.getWeight(),plantCharacteristic.getMaxNumberPerCell());
    }
}
