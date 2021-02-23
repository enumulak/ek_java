package in.geektrust.app;

import java.util.ArrayList;

public class Vehicle {

    // DATA
    private String name;

    private int maxSpeed;

    private int potholeTime;

    private ArrayList<String> weatherList = new ArrayList<String>();

    private int restrictedSpeed;

    private int minutesToTraverseOrbit;

    private boolean canBeUsedInCurrentWeather;

    // CONSTRUCTORS
    public Vehicle() {}

    public Vehicle(String name, int mSpeed, int pTime, ArrayList<WeatherType> fWeather) {

        this.name = name;
        this.maxSpeed = mSpeed;
        this.potholeTime = pTime;
        this.restrictedSpeed = 0;
        this.minutesToTraverseOrbit = 0;
        this.canBeUsedInCurrentWeather = false;

        if (fWeather != null && !fWeather.isEmpty()) {

            for (int i = 0; i < fWeather.size(); i++) {

                this.weatherList.add(fWeather.get(i).toString());
            }
        }
        else {
            System.err.println("Insufficient Data...");
        }
    }

    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getPotholeTime() {
        return potholeTime;
    }

    public void setPotholeTime(int potholeTime) {
        this.potholeTime = potholeTime;
    }

    public int getRestrictedSpeed() {
        return restrictedSpeed;
    }

    public void setRestrictedSpeed(int restrictedSpeed) {
        this.restrictedSpeed = restrictedSpeed;
    }

    public int getMinutesToTraverseOrbit() {
        return minutesToTraverseOrbit;
    }

    public void setMinutesToTraverseOrbit(int minutesToTraverseOrbit) {
        this.minutesToTraverseOrbit = minutesToTraverseOrbit;
    }

    public boolean isCanBeUsedInCurrentWeather() {
        return canBeUsedInCurrentWeather;
    }

    public void setCanBeUsedInCurrentWeather(boolean canBeUsedInCurrentWeather) {
        this.canBeUsedInCurrentWeather = canBeUsedInCurrentWeather;
    }

    public ArrayList<String> getWeatherList() {
        return this.weatherList;
    }

    public String getFromWeatherList(int index) {
        return this.weatherList.get(index);
    }

    // CUSTOM METHODS
    public void RequestSpeedAndMinutesSettingForOrbit(Orbit o) {

        if (o != null && canBeUsedInCurrentWeather) {

            int orbitSpeed = o.getTrafficSpeed();

            if (orbitSpeed < maxSpeed) {
                this.setRestrictedSpeed(orbitSpeed);
            }
            else {
                this.setRestrictedSpeed(this.maxSpeed);
            }
        }

        if(this.restrictedSpeed > 0) {
            this.calculateMinutesToTraverseOrbit(o);
        }
    }

    public final void RequestUsageForCurrentWeather(boolean flag)
    {
        if (flag)
        {
            this.setCanBeUsedInCurrentWeather(flag);
        }
    }

    private void calculateMinutesToTraverseOrbit(Orbit o)
    {
        double result = 0;

        double speed = (double) this.getRestrictedSpeed();
        double distance = (double) o.getDistance();

        double factor = distance / speed;
        factor *= 60;
        result = factor + (o.getPotholes() * this.getPotholeTime());

        this.setMinutesToTraverseOrbit((int)result);
    }
}
