package App.entity;

public class Airplane {
    private final String tailNumber;
    private final AirplaneModel model;

    public Airplane(String tailNumber, AirplaneModel model) {
        this.tailNumber = tailNumber;
        this.model = model;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public String getModel() {
        return model.getModelName();
    }

    public int getPassengerCapacity() {
        return model.getCapacity();
    }
}
