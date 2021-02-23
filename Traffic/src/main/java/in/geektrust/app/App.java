package in.geektrust.app;


public class App 
{
    public static void main( String[] args )
    {
         //String path = "C:\\TrafficInput\\rainy2.txt";

        try {

            String path = args[0];

            TrafficManager.getInstance().StartTrafficManagement(path);

        } catch (Exception e) {
            System.err.println("Looks like the correct arguments were not provided: " + e);
            e.printStackTrace();
        }
    }
}
