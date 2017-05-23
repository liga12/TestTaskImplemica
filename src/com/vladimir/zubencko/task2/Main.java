package com.vladimir.zubencko.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] names = {"G", "B", "T", "W"};  // array name city
        int[][] arr = {                         // route between cities
                {0, 1, 3, 0},
                {1, 0, 1, 4},
                {3, 1, 0, 1},
                {0, 4, 1, 0}};

        List<City> cities = new ArrayList<>(); // list cities
        for (int i = 0; i < arr.length; i++) {
            City city = new City(names[i], i, 0); //create city
            List<City> currentCities = new ArrayList<>();
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] != 0) {
                    currentCities.add(new City(names[j], j, arr[i][j])); //add neighbors
                }
            }
            city.setCities(currentCities);
            cities.add(city);
        }

        Route route = null;
        City sourceCity = cities.get(0);         // source city
        City DestinationCity = cities.get(3);   //destinationCity
        RouteService minRoute = new RouteService(sourceCity, DestinationCity, cities);
        try {
            route = minRoute.calculateMinRoute(); // get min route
        } catch (IllegalArgumentException e) {// illegal data
            System.out.println(e.getMessage()); // print message
        }
        if (route != null) {
            new Main().printRoute(route);
        }
    }

    // print route
    public void printRoute(Route route) {
        if (route.getRoutes().size() != 0) {
            for (Route route1 : route.getRoutes()) {
                System.out.print("cost = " + route1.getCost() + " route: ");
                for (City city : route1.getCities()) {
                    System.out.print("->" + city.getName());
                }
                System.out.println();
            }
        } else {
            System.out.println("Not found the rout");
        }

    }
}



