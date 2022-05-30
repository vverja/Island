package game;

import animal_world.Animal;
import animal_world.Plants;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Cell {
    @Getter private final int x,y;
    @Getter private final ArrayList<Direction> availableDirections;

    @Getter private final CopyOnWriteArrayList<Plants> plants = new CopyOnWriteArrayList<>();
    @Getter private final CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();

    public Cell(int x, int y, ArrayList<Direction> availableDirections) {
        this.x = x;
        this.y = y;
        this.availableDirections = availableDirections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public List<Animal> getListOfAnimalsByName(String name){
        return animals.stream()
                .filter(animal -> animal.getName().equals(name))
                .collect(Collectors.toList());
    }
}
