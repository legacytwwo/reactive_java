package App.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import App.entity.Airplane;
import App.entity.AirplaneModel;

public class AirplaneGenerator {
    private static final AirplaneModel[] MODELS = AirplaneModel.values();
    private static final Random random = new Random();

    public List<Airplane> generateFromSize(int poolSize) {
        List<Airplane> pool = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            AirplaneModel randomModel = MODELS[random.nextInt(MODELS.length)];
            int number = 10000 + random.nextInt(90000);
            String tailNumber = String.valueOf(number);
            pool.add(new Airplane(tailNumber, randomModel));
        }
        return pool;
    }
}