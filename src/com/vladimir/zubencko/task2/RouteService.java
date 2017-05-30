package com.vladimir.zubencko.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RouteService {

    public Route calculateMinRoute(List<City> cities, City sourceCity, City destinationCity) {
        Route mainRoute = new Route();
        validateData(cities, sourceCity, destinationCity);
        calculateFirsNeighbors(cities, sourceCity, mainRoute, destinationCity);
        calculateRoute(mainRoute, cities, destinationCity);
        return mainRoute;
    }

    private void validateData(List<City> cities, City sourceCity, City destinationCity) {
        if (cities == null || sourceCity.getIndex() == destinationCity.getIndex()) {
            throw new IllegalArgumentException("Illegal data");
        }
        boolean isSource = false;
        boolean isDestination = false;
        for (City city : cities) {
            if (city.getIndex() == sourceCity.getIndex()) {
                isSource = true;
            }
            if (city.getIndex() == destinationCity.getIndex()) {
                isDestination = true;
            }
        }
        if (!isSource && !isDestination) {
            throw new IllegalArgumentException("Illegal data");
        }
    }


    private void calculateFirsNeighbors(List<City> cities, City sourceCity, Route mainRoute, City destinationCity) {
        List<City> currentCities = new ArrayList<>(Collections.singletonList(cities.get(sourceCity.getIndex())));
        Route visitAtCities = new Route(currentCities);
        Route route = calculateNeighborRouts(sourceCity, visitAtCities, cities, destinationCity);
        mainRoute.setRoutes(route.getRoutes());
        checkMinRoute(mainRoute);
    }

    private void calculateRoute(Route mainRoute, List<City> cities, City destinationCity) {
        while (!mainRoute.isEndRoute()) {
            List<Route> routes = mainRoute.getRoutes();
            List<Route> oldRoutes = new ArrayList<>();
            List<Route> newRoutes = new ArrayList<>();
            for (Route visitAtCities : mainRoute.getRoutes()) {
                if (!visitAtCities.isEndRoute()) {
                    oldRoutes.add(visitAtCities);
                    Route neighborsRouts = calculateNeighborRouts
                            (visitAtCities.getDestination(), visitAtCities, cities, destinationCity);
                    for (Route current : neighborsRouts.getRoutes()) {
                        Route currentRoute = addRouts(current, visitAtCities);
                        newRoutes.add(currentRoute);
                    }
                }
            }
            editRoutes(routes, oldRoutes, newRoutes, mainRoute);
            checkMinRoute(mainRoute);
        }
    }

    private Route calculateNeighborRouts
            (City currentDestinationCity, Route visitAtCities, List<City> cities, City destinationCity) {
        Route route = new Route();
        City currentCity = cities.get(currentDestinationCity.getIndex());
        route.setSource(currentCity);
        List<Route> routes = new ArrayList<>();
        List<City> neighbors = currentCity.getNeighborCities();
        for (City neighbor : neighbors) {
            if (!isVisited(visitAtCities, neighbor)) {
                Route neighborRout = calculateRout(neighbor, currentCity, destinationCity);
                routes.add(neighborRout);
                route.setRoutes(routes);
            }
        }
        return route;
    }

    private boolean isVisited(Route visitAtCities, City neighbor) {
        for (City city : visitAtCities.getCities()) {
            if (city.getIndex() == neighbor.getIndex()) {
                return true;
            }
        }
        return false;
    }

    private Route calculateRout(City neighbor, City currentCity, City destinationCity) {
        Route currentRoute = new Route(neighbor.getCoast(), currentCity, neighbor);
        if (neighbor.getIndex() == destinationCity.getIndex()) {
            currentRoute.setEndRoute(true);
        }
        List<City> cities = new ArrayList<>(Arrays.asList(currentCity, neighbor));
        currentRoute.setCities(cities);
        return currentRoute;
    }

    private Route addRouts(Route current, Route visitAtCities) {
        int cost = visitAtCities.getCost() + current.getCost();
        Route currentRoute = new Route(cost, visitAtCities.getSource(), current.getDestination());
        currentRoute.setEndRoute(current.isEndRoute());
        List<City> cities = new ArrayList<>(visitAtCities.getCities());
        cities.add(currentRoute.getDestination());
        currentRoute.setCities(cities);
        return currentRoute;
    }

    private void editRoutes(List<Route> routes, List<Route> oldRoutes, List<Route> newRoutes, Route mainRoute) {
        if (newRoutes != null) {
            routes.removeAll(oldRoutes);
            routes.addAll(newRoutes);
        }
        checkMinMainRoute(mainRoute);
    }

    private void checkMinMainRoute(Route mainRoute) {
        boolean endRoute = true;
        for (Route route : mainRoute.getRoutes()) {
            if (!route.isEndRoute()) {
                endRoute = false;
                break;
            }
        }
        if (endRoute) {
            mainRoute.setEndRoute(true);
        }
    }

    private void checkMinRoute(Route mainRoute) {
        findEndMinRoute(mainRoute);
        List<Route> routes = mainRoute.getRoutes();
        Route minRoute = null;
        boolean isMinRoute = false;
        int minRouteCost = 0;
        for (int i = 0; i < routes.size(); i++) {
            Route currentRoute = routes.get(i);
            int currentRouteCost = currentRoute.getCost();
            if (currentRoute.isEndRoute()) {
                if (!isMinRoute) {
                    minRoute = routes.get(i);
                    isMinRoute = true;
                    minRouteCost = minRoute.getCost();
                } else if (currentRouteCost < minRouteCost) {
                    routes.remove(minRoute);
                    minRoute = currentRoute;
                    i--;
                } else if (currentRouteCost > minRouteCost) {
                    routes.remove(routes.get(i));
                    i--;
                }
            }
        }
    }

    private void findEndMinRoute(Route mainRoute) {
        Route minRoute = mainRoute.getRoutes().get(0);
        List<Route> removeRoutes = new ArrayList<>();
        List<Route> routes = mainRoute.getRoutes();
        for (Route route : routes) {
            if (minRoute.getCost() > route.getCost()) {
                minRoute = route;
            }
        }
        if (minRoute.isEndRoute()) {
            mainRoute.setEndRoute(true);
            for (Route route : routes) {
                if (!route.isEndRoute()) {
                    removeRoutes = new ArrayList<>(Collections.singletonList(route));
                }
            }
            routes.removeAll(removeRoutes);
        }
    }
}
