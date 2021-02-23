package in.geektrust.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class TrafficManager {

    // DATA
    private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
    private ArrayList<Orbit> orbits = new ArrayList<Orbit>();
    private ArrayList<Weather> weathers = new ArrayList<Weather>();

    private ArrayList<Vehicle> vehiclesForCurrentWeather = new ArrayList<Vehicle>();

    private Weather currentWeather;

    public String recommendedOrbit = "";
    public String recommendedVehicle = "";

    // Static Method that provides access to singleton instance of this class
    private static TrafficManager instance = new TrafficManager();
    public static TrafficManager getInstance() {
        return instance;
    }

    // Private Method to create required data
    private void CreateData() {

        this.weathers = new ArrayList<Weather> (

                Arrays.asList(new Weather(WeatherType.rainy, 0.2),
                        new Weather(WeatherType.sunny, -0.1),
                        new Weather(WeatherType.windy, 0.0))
        );

        this.vehicles = new ArrayList<Vehicle> (

                Arrays.asList(new Vehicle("BIKE", 10, 2, new ArrayList<WeatherType>(Arrays.asList(WeatherType.sunny, WeatherType.windy))),
                        new Vehicle("TUKTUK", 12, 1, new ArrayList<WeatherType>(Arrays.asList(WeatherType.sunny, WeatherType.rainy))),
                        new Vehicle("CAR", 20, 3, new ArrayList<WeatherType>(Arrays.asList(WeatherType.sunny, WeatherType.rainy, WeatherType.windy))))
        );

        this.orbits = new ArrayList<Orbit>(
                Arrays.asList(new Orbit("ORBIT1", 18, 20),
                        new Orbit("ORBIT2", 20, 10))
        );

    }

    private void setRequiredParameters(String cWeather, int oSpeed1, int oSpeed2) {

        // Set the Orbit speeds
        this.orbits.get(0).RequestTrafficSpeedSetting(oSpeed1);
        this.orbits.get(1).RequestTrafficSpeedSetting(oSpeed2);

        // Set the current weather
        for (int i = 0; i < this.weathers.size(); i++) {

            if (this.weathers.get(i).getWeatherType().toLowerCase().equals(cWeather.toLowerCase())) {

                this.currentWeather = this.weathers.get(i);
            }
        }

        // Set the vehicles that can be used in the current weather
        boolean flag = false;

        for (int i = 0; i < this.vehicles.size(); i++) {

            for (int j = 0; j < this.vehicles.get(i).getWeatherList().size(); j++) {

                if (this.vehicles.get(i).getFromWeatherList(j).toLowerCase().equals(this.currentWeather.getWeatherType().toLowerCase())) {

                    flag = true;

                    this.vehicles.get(i).setCanBeUsedInCurrentWeather(flag);

                    if (this.vehicles.get(i).isCanBeUsedInCurrentWeather()) {

                        this.vehiclesForCurrentWeather.add(this.vehicles.get(i));
                    }
                }
            }
        }
    }

    // Public Method to Start Traffic Management
    public void StartTrafficManagement(String inputFile) {

        CreateData();

        // Get input parameters from external file
        if ((new File(inputFile)).isFile()) {

            try {

                File file = new File(inputFile);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String s;

                while ((s = br.readLine()) != null) {

                    String[] lines = s.split(" ");

                    String cWeather = "";
                    int orbitSpeed1 = 0;
                    int orbitSpeed2 = 0;

                    if (lines.length == 3) {
                        cWeather = lines[0];
                        orbitSpeed1 = Integer.parseInt(lines[1]);
                        orbitSpeed2 = Integer.parseInt(lines[2]);
                    } else {
                        System.err.println("There was a problem with the provided data");
                    }

                    setRequiredParameters(cWeather, orbitSpeed1, orbitSpeed2);

                    // Run a loop to evaluate All Orbits
                    for (int i = 0; i < this.orbits.size(); i++) {

                        OrbitEvaluator.getInstance().evaluateOrbit(this.orbits.get(i), this.currentWeather, this.vehiclesForCurrentWeather);
                    }

                    printOutput();

                    // Reset all data, including the Orbit Evaluator
                    cWeather = "";
                    orbitSpeed1 = 0;
                    orbitSpeed2 = 0;

                    OrbitEvaluator.getInstance().resetData();
                }

            } catch (Exception e) {
                System.err.println("There was an error with the input file: " + e);
                e.printStackTrace();
            }
        }
    }

    private void printOutput() {

        if (!recommendedOrbit.equals("") && !recommendedVehicle.equals(""))
        {
            System.out.println(recommendedVehicle + " " + recommendedOrbit);
        }
        else
        {
            System.out.println("No Output");
        }

        this.recommendedOrbit = "";
        this.recommendedVehicle = "";
    }
}
