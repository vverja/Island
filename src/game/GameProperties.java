package game;


import lombok.Data;

import java.util.Arrays;

@Data
public class GameProperties {
    private Characteristics[] animalCharacteristics;
    private int fieldColumns, fieldRows;
    private int tactDuration;
    private int deathSatietyPercent;
    private int breedSatietyPercent;
    private int satietyConsumptionPerTurn;

    public Characteristics getCharacteristicsByName(String name) throws RuntimeException{
        return Arrays.stream(animalCharacteristics)
                .filter(characteristics -> name.equals(characteristics.getName()))
                .findFirst().orElseThrow();
    }
}
