package game;

import animal_world.*;
import animal_world.factory.AnimalsFactory;
import animal_world.factory.PlantsFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Game {

    private static GameProperties gameProperties;
    private static final List<Cell> cells = new CopyOnWriteArrayList<>();
    private static final Statistics statistics = new Statistics();
    private static Map<String, Map<String, Integer>> probability = new HashMap<>();
    private static final Map<Cell, ArrayList<Animal> > animalDelayList = new ConcurrentHashMap<>();

    public static void start(){
        //получаем из джсон начальные настройки и вероятности
        fillGameProperties();
        //создаем игровое поле
        createIsland();
        //заполняем ячейки животными и растениями
        createLife();
        //начинаем игру
        beginLifeCycle();
    }

    private static void beginLifeCycle() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            //увеличиваем значение такта
            statistics.tactIncrement();
            //печатаем статистику
            statistics.printStatistics();
            //печатаем игровое поле
            printField();
            //едим
            eat();
            //размножаемсмя
            toBreed();
            //дивгаемся
            move();
        },0, gameProperties.getTactDuration(),TimeUnit.MILLISECONDS);
        try {
            //цикл бесконечный
            scheduledExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void eat() {
        ExecutorService service = Executors.newCachedThreadPool();
        cells.forEach((cell) ->
                service.submit(()->
                        cell.getAnimals().forEach(animal -> animal.eat(cell))
                ));
        service.shutdown();
        try {
            if(service.awaitTermination(gameProperties.getTactDuration()/4, TimeUnit.MILLISECONDS))
                //если не уклаываемся во время четверти такта, задачи не выполняем
                service.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void toBreed() {
        ExecutorService service = Executors.newCachedThreadPool();
        cells.forEach((cell) ->{
            service.submit(()->
                cell.getAnimals().forEach(animal -> animal.toBreed(cell)));
            service.submit(()->
                cell.getPlants().forEach(plants -> plants.toBreed(cell)));
        });
        service.shutdown();

        try {
            if(!service.awaitTermination(gameProperties.getTactDuration()/4, TimeUnit.MILLISECONDS))
                //если не уклаываемся во время четверти такта, задачи не выполняем
                service.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moveAnimalDelayList();
    }

    private static void move() {
        ExecutorService service = Executors.newCachedThreadPool();
        cells.forEach((cell) ->{
            if(ThreadLocalRandom.current().nextInt(2)==0)
                return;
            service.submit(()->
                cell.getAnimals().forEach(animal -> animal.move(cell))
            );
        });
        service.shutdown();
        try {
            if(!service.awaitTermination(gameProperties.getTactDuration()/4, TimeUnit.MILLISECONDS))
                service.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moveAnimalDelayList();
    }

    public static Map<Cell, ArrayList<Animal>> getAnimalDelayList() {
        return animalDelayList;
    }

    public static void putAnimalToDelayList(Cell cell, Animal animal){
        Map<Cell, ArrayList<Animal>> animalDelayMovementList = Game.getAnimalDelayList();
        if (animalDelayMovementList.containsKey(cell)) {
            ArrayList<Animal> animals = animalDelayMovementList.get(cell);
            animals.add(animal);
        }else {
            ArrayList<Animal> animals = new ArrayList<>();
            animals.add(animal);
            animalDelayMovementList.put(cell,  animals);
        }
    }
    private static void moveAnimalDelayList() {
        ExecutorService service = Executors.newCachedThreadPool();
        animalDelayList.forEach((cell, animalList)->
            service.submit(()-> {
                cell.getAnimals().addAll(animalList);
//                animalDelayList.remove(cell);
            }
        ));
        service.shutdown();
        try {
            if(service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS))
                animalDelayList.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createIsland(){
        int rows = gameProperties.getFieldRows();
        int cols = gameProperties.getFieldColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ArrayList<Direction> availableDirections = new ArrayList<>(
                        List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
                );
                if (i==0)
                    availableDirections.remove(Direction.LEFT);
                if (j==0)
                    availableDirections.remove(Direction.UP);
                if (j==cols-1)
                    availableDirections.remove(Direction.RIGHT);
                if (i==rows-1)
                    availableDirections.remove(Direction.DOWN);
                cells.add(new Cell(j,i,availableDirections));
            }
        }
    }

    private static void createLife() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        PlantsFactory plantsFactory = new PlantsFactory();
        AnimalsFactory animalsFactory = new AnimalsFactory();
        Map<String, Characteristics> characteristicsMap = Arrays.stream(gameProperties.getAnimalCharacteristics())
                                                        .collect(Collectors.toMap(Characteristics::getName, el -> el));

        int plantsMaxNumberPerCell = characteristicsMap.get("Растения").getMaxNumberPerCell();
        cells.forEach((cell) -> {
                executorService.submit(()-> {
                    long qty = ThreadLocalRandom.current().nextInt(plantsMaxNumberPerCell);
                    for (int i = 0; i < qty; i++) {
                        cell.getPlants().add(plantsFactory.createUnit("Растения", gameProperties));
                    }
                    statistics.born("Растения",qty);
            });
        });
                cells.forEach(cell ->
                    executorService.submit(() ->{
                    for (Characteristics animalCharacteristic : gameProperties.getAnimalCharacteristics()) {
                        if ("Растения".equals(animalCharacteristic.getName()))
                            continue;
                        long qty = ThreadLocalRandom.current().nextInt(animalCharacteristic.getMaxNumberPerCell());
                        for (int i = 0; i < qty; i++) {
                            cell.getAnimals().add(animalsFactory.createUnit(animalCharacteristic.getName(), gameProperties));
                        }
                        statistics.born(animalCharacteristic.getName(), qty);
                    }
                })
        );
        executorService.shutdown();
        try {
            executorService.awaitTermination(gameProperties.getTactDuration(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fillGameProperties(){
        Gson gson=new Gson();
        try {
            Type type = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
            probability = gson.fromJson(Files.readString(Path.of("probability.json")), type);
            gameProperties  = gson.fromJson(Files.readString(Path.of("characteristics.json")), GameProperties.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProperties getGameProperties() {
        return gameProperties;
    }

    public static Map<String, Map<String, Integer>> getProbability() {
        return probability;
    }

    public static Optional<Cell> getCellByCoordinates(int x, int y){
        return cells.stream().filter(cell -> cell.getX() == x && cell.getY() == y).findFirst();

    }

    public static Statistics getStatistics(){
        return statistics;
    }

    private static void printField() {
        cells.forEach(Game::printCell);
    }

    private static void printCell(Cell cell){
        Map<String, Long> collect = cell.getAnimals().stream()
                                            .collect(Collectors.groupingBy(Animal::toString, Collectors.counting()));
        Optional<Map.Entry<String, Long>> max = collect.entrySet().stream().max(((o1, o2) -> (int) (o1.getValue() - o2.getValue())));
        Map.Entry<String, Long> result = max.orElseThrow();
        System.out.print(result.getKey());
        if (cell.getX()==gameProperties.getFieldColumns()-1)
            System.out.println();
    }
}
