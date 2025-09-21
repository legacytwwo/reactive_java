package App.generator;

import java.util.List;
import java.util.Random;

import App.entity.Airplane;

public class AirplaneGenerator {
    private record AirplaneModel(String model, int capacity) {
    }

    private static final List<AirplaneModel> AIRPLANE_MODELS = List.of(
            new AirplaneModel("Boeing 737", 200),
            new AirplaneModel("Airbus A320", 150),
            new AirplaneModel("Sukhoi Superjet 100", 100));
    private static final Random random = new Random();

    public Airplane generate() {
        AirplaneModel randomModel = AIRPLANE_MODELS.get(random.nextInt(AIRPLANE_MODELS.size()));
        int number = 10000 + random.nextInt(90000);
        String tailNumber = String.valueOf(number);
        return new Airplane(
                tailNumber,
                randomModel.model(),
                randomModel.capacity());
    }
}