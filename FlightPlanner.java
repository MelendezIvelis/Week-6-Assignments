package week5;

import java.util.*;
import acm.program.*;
import java.io.*;
import acm.util.*;

	
	import acm.program.ConsoleProgram;
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.util.ArrayList;
	import java.util.HashMap;

	public class FlightPlanner extends ConsoleProgram {

	    private static final String FILE = "flights.txt";

	    private ArrayList<String> path = new ArrayList<>();
	    private HashMap<String, ArrayList<String>> possibleDestinations = new HashMap<>();

	    public void run(){
	        dataRead(FILE);
	        intro();
	        planTrip();
	    }

	    private void intro(){
	        println("Welcome to Flight Planner!");
	        // Display all cities in the database
	        println("Here is a list of all the cities in our database:");
	        for (String name: possibleDestinations.keySet()){
	            println(" - " + name);
	        }
	    }

	    // Reads the flights file and processes each line
	    private void dataRead(String filename){
	        BufferedReader br;
	        try {
	            br = new BufferedReader(new FileReader(filename));
	            while (true){
	                String line = br.readLine();
	                if (line == null) break;
	                process(line);
	            }
	        } catch (Exception e){
	            println("No file found");
	        }
	    }

	    // Separates the line into starting cities and possible destinations.
	    // Adds the resulting values in the "possibleDestinations" hash
	    private void process(String line){
	        int separator = line.indexOf(" -> ");
	        String start = line.substring(0, separator);
	        String destination = line.substring(separator + " -> ".length());

	        if (!possibleDestinations.containsKey(start)){
	            possibleDestinations.put(start, new ArrayList<>());
	        }
	        possibleDestinations.get(start).add(destination);
	    }

	    // Main method that handles the user input
	    private void planTrip(){
	        String startingCity = startingCity();
	        String currentCity  = startingCity;
	        getDestinationCities(startingCity);
	        path.add(startingCity);

	        while (true){
	            currentCity = currentCityDestinations(currentCity);
	            path.add(currentCity);
	            if (currentCity.equals(startingCity)){
	                println(printRoute(path));
	                break;
	            }
	            getDestinationCities(currentCity);
	        }
	    }

	    // Prints the complete path of the round trip
	    private String printRoute(ArrayList<String> route){
	        String result = "";

	        for (int i = 0; i < route.size(); i++){
	            result += route.get(i);
	            if (i < route.size() - 1) {
	                result += " -> ";
	            }
	        }
	        return result;
	    }

	    // Asks the user for a starting city, checks if it's valid and returns it
	    private String startingCity(){
	        println("Let's plan a round trip");
	        println("-~-~-~-~-~-~-~-~-~-~-~-");

	        while (true){
	            String startingCity = readLine("Enter the starting city: ");
	            if (possibleDestinations.containsKey(startingCity)) {
	                return startingCity;
	            }
	            println("Please type a city from the list.");
	        }
	    }

	    // Asks the user for a destination city, checks if it's valid and returns it
	    // Also prints the available destinations
	    private String currentCityDestinations(String city){
	        while (true){
	            String nextCity = readLine("Where do you want to go from " + city + "? ");
	            if (possibleDestinations.containsKey(nextCity)) {
	                return nextCity;
	            }
	            println("You can't get to that city by a direct flight.");
	        }
	    }

	    // Prints the destination cities corresponding to the user input
	    private void getDestinationCities(String currentCity){
	        println("From " + currentCity + " you can fly directly to: ");
	        if (possibleDestinations.containsKey(currentCity)){
	            ArrayList<String> destinations;
	            destinations = possibleDestinations.get(currentCity);
	            for (String city: destinations){
	                println(" - " + city);
	            }
	        }
	    }

	}