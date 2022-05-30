package animal_world.factory;

import animal_world.Unit;
import game.GameProperties;
//попытался реализовать шаблон Фабричный метод
public interface LifeFactory {
    Unit createUnit(String name, GameProperties properties);
}
