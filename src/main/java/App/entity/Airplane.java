package App.entity;

public class Airplane {
    private final String tailNumber;
    private final String model;
    private final int passengerCapacity;

    public Airplane(String tailNumber, String model, int passengerCapacity) {
        this.tailNumber = tailNumber;
        this.model = model;
        this.passengerCapacity = passengerCapacity;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public String getModel() {
        return model;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }
}
