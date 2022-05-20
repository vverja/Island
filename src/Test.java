import animal_world.Animal;
import animal_world.Characteristics;
import animal_world.real_animals.Goat;
import animal_world.real_animals.Wolf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {


        Gson gson=new Gson();
        try {
            Type type = new TypeToken<ArrayList<Characteristics>>(){}.getType();
            List<Characteristics> characteristics  = gson.fromJson(Files.readString(Path.of("characteristics.json")), type);
            characteristics.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
