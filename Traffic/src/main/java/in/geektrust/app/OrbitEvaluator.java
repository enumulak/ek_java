package in.geektrust.app;

import java.util.ArrayList;

public class OrbitEvaluator {

    // DATA
    private Orbit orbit;

    // This list stores the vehicles that can be used in current weather - passed in by traffic manager
    private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    private Weather weather;

    private ArrayList<OrbitRecommender> orbitRecommenderList = new ArrayList<OrbitRecommender>();

    // Static Method that provides access to singleton instance of this class
    private static OrbitEvaluator instance = new OrbitEvaluator();
    public static OrbitEvaluator getInstance() {
        return instance;
    }

    // CUSTOM METHODS
    public void evaluateOrbit(Orbit orbit, Weather currentWeather, ArrayList<Vehicle> vehicles) {

        if (orbit != null && currentWeather != null && vehicles != null) {

            this.orbit = orbit;
            this.weather = currentWeather;
            this.vehicles = vehicles;
        }

        // We first send the current weather's pothole factor to the current orbit, so that it can re-calculate its pothole count
        this.orbit.RequestPotholeAssessment(this.weather.getPotholeFactor());

        // Then, vehicles (that can be used in current weather) are requested to set speed restrictions and calculate the time they take to traverse current orbit
        for (int i = 0; i < this.vehicles.size(); i++) {

            this.vehicles.get(i).RequestSpeedAndMinutesSettingForOrbit(this.orbit);
        }

        // We can thereafter initiate our recommendations to the traffic manager
        initiateVehicleAndOrbitRecommendation();
    }

    private void initiateVehicleAndOrbitRecommendation() {

        // We need to get the Vehicle that has the Lowest value for 'MinutesToTraverseOrbit' (meaning that the Vehicle traverses the Orbit faster..)
        // We loop through the Vehicle List and get the Vehicle Object that has the 'lowest' Minutes
        int minutes = 10000;
        Vehicle fastestVehicleForOrbit = new Vehicle();

        for (int i = 0; i < this.vehicles.size(); i++) {
            if (this.vehicles.get(i).getMinutesToTraverseOrbit() < minutes) {

                minutes = this.vehicles.get(i).getMinutesToTraverseOrbit();
                fastestVehicleForOrbit = this.vehicles.get(i);
            }
        }

        try {
            this.orbitRecommenderList.add(new OrbitRecommender(fastestVehicleForOrbit.getName(), this.orbit.getName(), fastestVehicleForOrbit.getMinutesToTraverseOrbit()));
        } catch (NullPointerException e) {
            System.err.println("Object not found: " + e);
            e.printStackTrace();
        }

        recommendVehicleAndOrbit();
    }

    private void recommendVehicleAndOrbit() {

        OrbitRecommender finalRecommendation = new OrbitRecommender();

        try {

            // We need to check which vehicle (if there are multiple) from the recommender list has the lowest minutes
            int minutes = 10000;

            for (int i = 0; i < this.orbitRecommenderList.size(); i++) {

                if (this.orbitRecommenderList.get(i).getMinutesForVehicle() < minutes) {

                    minutes = this.orbitRecommenderList.get(i).getMinutesForVehicle();
                    finalRecommendation = this.orbitRecommenderList.get(i);
                }
            }

            TrafficManager.getInstance().recommendedVehicle = finalRecommendation.getVehicleName();
            TrafficManager.getInstance().recommendedOrbit = finalRecommendation.getOrbitName();

        } catch (NullPointerException e) {
            System.err.println("Some problem with data: " + e);
            e.printStackTrace();
        }
    }

    public void resetData() {

        this.orbitRecommenderList.clear();
        this.vehicles.clear();
    }
}
