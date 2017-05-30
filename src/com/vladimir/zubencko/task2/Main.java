package com.vladimir.zubencko.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] cityNames = {"G", "B", "T", "W"};
        int[][] adjacencyTable = {
                {0, 1, 3, 0},
                {1, 0, 1, 4},
                {3, 1, 0, 1},
                {0, 4, 1, 0}};

        List<City> cities = new ArrayList<>();
        for (int i = 0; i < adjacencyTable.length; i++) {
            City city = new City(cityNames[i], i, 0);
            List<City> currentCities = new ArrayList<>();
            for (int j = 0; j < adjacencyTable.length; j++) {
                if (adjacencyTable[i][j] != 0) {
                    currentCities.add(new City(cityNames[j], j, adjacencyTable[i][j]));
                }
            }
            city.setNeighborCities(currentCities);
            cities.add(city);
        }

        City sourceCity = cities.get(0);
        City destinationCity = cities.get(3);
        RouteService minRoute = new RouteService();
        try {
            Route route = minRoute.calculateMinRoute(cities, sourceCity, destinationCity);
            if (route != null) {
                new Main().printRoute(route);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printRoute(Route route) {
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



