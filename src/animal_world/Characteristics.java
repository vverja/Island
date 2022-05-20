package animal_world;

public class Characteristics {
    private String name;
    private double weight;
    private int maxNumberPerCell;
    private int speed;
    private double foodForSatietyInKilos;

    public Characteristics(String name, double weight, int maxNumberPerCell, int speed, double foodForSatietyInKilos) {
        this.name = name;
        this.weight = weight;
        this.maxNumberPerCell = maxNumberPerCell;
        this.speed = speed;
        this.foodForSatietyInKilos = foodForSatietyInKilos;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxNumberPerCell() {
        return maxNumberPerCell;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodForSatietyInKilos() {
        return foodForSatietyInKilos;
    }

    @Override
    public String toString() {
        return "Characteristics{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", maxNumberPerCell=" + maxNumberPerCell +
                ", speed=" + speed +
                ", foodForSatietyInKilos=" + foodForSatietyInKilos +
                '}';
    }
}
