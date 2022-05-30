package animal_world;

import animal_world.factory.AnimalsFactory;
import game.Cell;
import game.Direction;
import game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Unit{
    @Getter private final int speed;
    @Getter private final double foodForSatietyInKilos;
    @Getter @Setter double satietyPercent;
    public Animal(String name, double weight, int maxNumberPerCell,
                  int speed,
                  double foodForSatietyInKilos) {
        super(name, weight, maxNumberPerCell);
        this.speed = speed;
        this.foodForSatietyInKilos = foodForSatietyInKilos;
        this.satietyPercent = 100.0;
    }

    public abstract void eat(Cell cell);
    //передвижение животного
    public void move(Cell cell){
        //Вероятность перемещения 50/50
        if(ThreadLocalRandom.current().nextInt(2)==0)
            return;
        //проверяем есть ли у животного на перемещение достаточно сил
        if (starvation(cell)){
            return;
        }
        //уменьшаем сытость
        satietyPercent-=Game.getGameProperties().getSatietyConsumptionPerTurn();
        //получаем количество клеток на которое перемещается животное
        int speed = ThreadLocalRandom.current().nextInt(getSpeed());
        if (speed==0)
            return;
        //получаем ячейку по скорости и направлению
        Cell cellByCoordinates = getCellByDirection(cell, speed);
        if (!cell.equals(cellByCoordinates)) {
            //добавляем животное в список на перемещение
            Game.putAnimalToDelayList(cellByCoordinates, this);
            cell.getAnimals().remove(this);
        }

    }

    private Cell getCellByDirection(Cell cell, int speed) {
        //получаем список доступных направлений
        ArrayList<Direction> availableDirections = cell.getAvailableDirections();
        //проходимся по списку и согласно скорости определяем правильные координаты
        for (Direction direction: availableDirections
             ) {
            Optional<Cell> foundCell = switch (direction) {
                case UP -> Game.getCellByCoordinates(cell.getX(), cell.getY() - speed);
                case DOWN -> Game.getCellByCoordinates(cell.getX(), cell.getY() + speed);
                case RIGHT -> Game.getCellByCoordinates(cell.getX() + speed, cell.getY());
                case LEFT -> Game.getCellByCoordinates(cell.getX() - speed, cell.getY());
            };
            if (foundCell.isPresent()) {
                //проверяем можем ли мы переместится в эту клетку из-за перенаселения
                int sizeOfPresentAnimals =  foundCell.get().getListOfAnimalsByName(getName()).size();
                if (sizeOfPresentAnimals<getMaxNumberPerCell())
                    return foundCell.get();
            }
        }
        return cell;
    }
    //проверяем если голод равен или меньше процента сытости при котором животное умирает
    private boolean starvation(Cell cell) {
        if (satietyPercent<=Game.getGameProperties().getDeathSatietyPercent()){
            Game.getStatistics().death(getName(),1L);
            cell.getAnimals().remove(this);
            return true;
        }
        return false;
    }


    @Override
    public void toBreed(Cell cell) {
        //Добавил вероятность 25% размножится животное или нет
        if(ThreadLocalRandom.current().nextInt(4)!=0)
            return;
        //если сытости недостаточно - не размножаемся
        if (satietyPercent>=Game.getGameProperties().getBreedSatietyPercent()){
            long count = cell.getListOfAnimalsByName(getName()).size();
            if (count<=1) return;
            Map<Cell, ArrayList<Animal>> animalDelayList = Game.getAnimalDelayList();
            long animalsAlreadyBorn = 0;
            if (animalDelayList.containsKey(cell)) {
                ArrayList<Animal> animals = animalDelayList.get(cell);
                animalsAlreadyBorn = animals.stream()
                        .filter(animal -> animal.getName().equals(getName()))
                        .count();
            }

            if (animalsAlreadyBorn>getMaxNumberPerCell()/2)
                return;
            Animal unit = new AnimalsFactory().createUnit(getName(), Game.getGameProperties());
            Game.putAnimalToDelayList(cell, unit);
            satietyPercent -= Game.getGameProperties().getSatietyConsumptionPerTurn();
            Game.getStatistics().born(getName(), 1L);
       }
    }
}
