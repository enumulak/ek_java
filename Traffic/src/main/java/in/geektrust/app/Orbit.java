package in.geektrust.app;

public class Orbit {

    // DATA
    private String name;

    private int distance;

    private int potholes;

    private int trafficSpeed;

    // CONSTRUCTOR
    public Orbit(String name, int distance, int potholes) {

        this.setName(name);
        this.setDistance(distance);
        this.setPotholes(potholes);
        this.setTrafficSpeed(0);
    }

    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPotholes() {
        return potholes;
    }

    public void setPotholes(int potholes) {
        this.potholes += potholes;
    }

    public int getTrafficSpeed() {
        return trafficSpeed;
    }

    public void setTrafficSpeed(int trafficSpeed) {
        this.trafficSpeed = trafficSpeed;
    }

    // CUSTOM METHODS
    public void RequestTrafficSpeedSetting(int speed) {

        if (speed > 0) {
            this.setTrafficSpeed(speed);
        }
    }

    public void RequestPotholeAssessment(double phFactor) {

        int result = (int) Math.round(this.potholes * phFactor);

        this.setPotholes(result);
    }
}
