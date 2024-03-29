package Fly;

import Aircraft.AircraftFactory;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import files.writing;


public class simulator {

    private static List<Flyable> flyables = new ArrayList<Flyable>();
    private static WeatherTower weatherTower;

    public static void main(String[] arg) throws InterruptedException {

        try
        {
            writing.openFile();
            BufferedReader buff = new BufferedReader(new FileReader(arg[0]));
            String line = buff.readLine();
            line = line.trim();
            if (line != null)
            {
                weatherTower = new WeatherTower();
                int simulation = Integer.parseInt(line.split(" ")[0]);
                if (simulation < 0)
                {
                    System.out.println("Invalid simulation count" + simulation);
                    System.exit(1);
                }
                while ((line = buff.readLine()) != null)
                {
                    line = line.trim();
                    if (line != null && line.split(" ").length == 5) {
                        Flyable flyable = AircraftFactory.newAircraft(line.split(" ")[0], line.split(" ")[1],
                                Integer.parseInt(line.split(" ")[2]), Integer.parseInt(line.split(" ")[3]),
                                Integer.parseInt(line.split(" ")[4]));
                        flyables.add(flyable);
                    }
                }

                for (Flyable flyable : flyables) {
                    flyable.registerTower(weatherTower);
                }
                for (int i = 1; i <= simulation; i++) {
                    writing.WriteToFile("\n" + "simulation: " + i +"\n");
                    weatherTower.changeWeather();
                }
            }
            writing.closeFile();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found" + arg[0]);
        }
        catch (IOException e)
        {
            System.out.println("error occurred while reading the file");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("No file specified");
        }
    }
}
