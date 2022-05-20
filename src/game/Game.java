package game;

import animal_world.*;
import animal_world.real_animals.Wolf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;


public class Game {
    private static final int MAX_ROWS = 100;
    private static final int MAX_COLUMNS = 20;
    private static final List<Cell> cells = new ArrayList<>();
    private static final List<Plants> plants = new ArrayList<>();
    private static final List<Predator> predators = new ArrayList<>();
    private static final List<Herbivore> herbivores = new ArrayList<>();
    private static Map<Animal, Map<Animal, Integer>> probability = new HashMap<>();

    public static void start(){
        createIsland();
        createLife();

    }

    private static void createIsland(){
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                ArrayList<Direction> availableDirections = new ArrayList<>(
                        List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
                );
                if (i==0)
                    availableDirections.remove(Direction.LEFT);
                if (j==0)
                    availableDirections.remove(Direction.UP);
                if (j==MAX_COLUMNS-1)
                    availableDirections.remove(Direction.RIGHT);
                if (i==MAX_ROWS-1)
                    availableDirections.remove(Direction.DOWN);
                cells.add(new Cell(j,i,availableDirections));
            }
        }
    }

    private static void createLife(){
        ExecutorService executorService = Executors.newCachedThreadPool();

        cells.forEach((cell)->{
            plants.add(new Plants(1, 1000, cell));
        });

    }
}
