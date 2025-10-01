package App.entity;

public enum AirplaneModel {
    BOEING_737_800("Boeing 737-800", 189),
    AIRBUS_A320("Airbus A320", 180),
    SUKHOI_SUPERJET_100("Sukhoi Superjet 100", 101),
    BOEING_777_300("Boeing 777-300ER", 451),
    AIRBUS_A350("Airbus A350-900", 325),
    EMBRAER_190("Embraer E190", 114);

    private final String modelName;
    private final int capacity;

    AirplaneModel(String modelName, int capacity) {
        this.modelName = modelName;
        this.capacity = capacity;
    }

    public String getModelName() {
        return modelName;
    }

    public int getCapacity() {
        return capacity;
    }
}