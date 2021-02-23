package in.geektrust.app;

public class OrbitRecommender {

    // DATA
    private String vehicleName;

    private String orbitName;

    private int minutesForVehicle;

    // CONSTRUCTORS
    public OrbitRecommender(){}
    public OrbitRecommender(String vehicleName, String orbitName, int minutesForVehicle) {

        this.setVehicleName(vehicleName);
        this.setOrbitName(orbitName);
        this.setMinutesForVehicle(minutesForVehicle);
    }

    // GETTERS AND SETTERS
    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getOrbitName() {
        return this.orbitName;
    }

    public void setOrbitName(String orbitName) {
        this.orbitName = orbitName;
    }

    public int getMinutesForVehicle() {
        return this.minutesForVehicle;
    }

    public void setMinutesForVehicle(int minutesForVehicle) {
        this.minutesForVehicle = minutesForVehicle;
    }
}
