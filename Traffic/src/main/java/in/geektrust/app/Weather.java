package in.geektrust.app;

public class Weather {

    // DATA
    private WeatherType weatherType;

    private double potholeFactor;

    // CONSTRUCTOR
    public Weather(WeatherType weatherType, double potholeFactor) {

        this.weatherType = weatherType;
        this.potholeFactor = potholeFactor;
    }

    public String getWeatherType() {
        return weatherType.toString();
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public double getPotholeFactor() {
        return potholeFactor;
    }

    public void setPotholeFactor(double potholeFactor) {
        this.potholeFactor = potholeFactor;
    }
}
