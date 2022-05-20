package animal_world;


import game.Cell;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

public class Plants extends Unit{
    @Getter
    @Setter
    private int number;
    @Getter
    private final Cell cell;

    public Plants(double weight, int maxNumberPerCell, Cell cell) {
        super("Растение", weight, maxNumberPerCell);
        this.cell = cell;
        number = ThreadLocalRandom.current().nextInt(getMaxNumberPerCell());
    }

    @Override
    public void toBreed() {
        if (number<getMaxNumberPerCell())
            number+= ThreadLocalRandom.current().nextInt(getMaxNumberPerCell()-number);
    }

    @Override
    public String toString() {
        return "\\uD83C\\uDF3F";
    }
}
