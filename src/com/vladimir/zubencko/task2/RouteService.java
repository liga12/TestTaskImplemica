package com.vladimir.zubencko.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Computes the minimum path between two cities
 *
 * @author Vladimir Zubencko
 * @version 1.0
 */
public class RouteService {

    /**
     * Method manager
     *
     * @param cities          list cities with neighbors
     * @param sourceCity      source city
     * @param destinationCity destination city
     * @return mainRoute - list of one or more minimal routes
     */
    public Route calculateMinRoute(List<City> cities, City sourceCity, City destinationCity) {
        Route mainRoute = new Route();
        validateData(cities, sourceCity, destinationCity);
        calculateFirsNeighbors(cities, sourceCity, mainRoute, destinationCity);
        calculateRoute(mainRoute, cities, destinationCity);
        return mainRoute;
    }

    /**
     * Check the data for validity
     *
     * @param cities          list cities with neighbors
     * @param sourceCity      source city
     * @param destinationCity destination city
     */
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

    /**
     * Calculates the first routes from the source city
     *
     * @param cities          list cities with neighbors
     * @param sourceCity      source city
     * @param mainRoute       main route that stores the minimum routes
     * @param destinationCity destination city
     */
    private void calculateFirsNeighbors(List<City> cities, City sourceCity, Route mainRoute, City destinationCity) {
        List<City> currentCities = new ArrayList<>(Collections.singletonList(cities.get(sourceCity.getIndex())));
        Route visitAtCities = new Route(currentCities);
        Route route = calculateNeighborRouts(sourceCity, visitAtCities, cities, destinationCity);
        mainRoute.setRoutes(route.getRoutes());
        checkMinRoute(mainRoute);
    }

    /**
     * Calculates one or more minimal routes
     *
     * @param mainRoute       main route that stores the minimum routes
     * @param cities          list cities with neighbors
     * @param destinationCity destination city
     */
    private void calculateRoute(Route mainRoute, List<City> cities, City destinationCity) {
        while (!mainRoute.isEndRoute()) {
            List<Route> oldRoutes = new ArrayList<>();
            List<Route> newRoutes = new ArrayList<>();
            for (Route route : mainRoute.getRoutes()) {
                if (!route.isEndRoute()) {
                    oldRoutes.add(route);
                    Route neighborRouts = calculateNeighborRouts
                            (route.getDestination(), route, cities, destinationCity);
                    for (Route neighborRout : neighborRouts.getRoutes()) {
                        Route currentRoute = addRouts(neighborRout, route);
                        newRoutes.add(currentRoute);
                    }
                }
            }
            editRoutes(oldRoutes, newRoutes, mainRoute);
            checkMinRoute(mainRoute);
        }
    }

    /**
     * Calculate the routes from the current city to neighbors
     *
     * @param currentDestinationCity current city far route
     * @param currentRoute           current route
     * @param cities                 list cities with neighbors
     * @param destinationCity        destination city
     * @return routes to neighbors
     */
    private Route calculateNeighborRouts
    (City currentDestinationCity, Route currentRoute, List<City> cities, City destinationCity) {
        Route route = new Route();
        City currentCity = cities.get(currentDestinationCity.getIndex());
        route.setSource(currentCity);
        List<Route> routes = new ArrayList<>();
        List<City> neighbors = currentCity.getNeighborCities();
        for (City neighbor : neighbors) {
            if (!isVisited(currentRoute, neighbor)) {
                Route neighborRout = calculateRout(neighbor, currentCity, destinationCity);
                routes.add(neighborRout);
                route.setRoutes(routes);
            }
        }
        return route;
    }

    /**
     * Checks the route route visited city
     *
     * @param currentRoute current route
     * @param neighbor     neighbor city
     * @return <tt>true</tt> if city was visited
     */
    private boolean isVisited(Route currentRoute, City neighbor) {
        for (City city : currentRoute.getCities()) {
            if (city.getIndex() == neighbor.getIndex()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate route from current city to neighbor
     *
     * @param neighbor        city neighbor
     * @param currentCity     current city
     * @param destinationCity destination city
     * @return route from current city to neighbor
     */
    private Route calculateRout(City neighbor, City currentCity, City destinationCity) {
        Route currentRoute = new Route(neighbor.getCost(), currentCity, neighbor);
        if (neighbor.getIndex() == destinationCity.getIndex()) {
            currentRoute.setEndRoute(true);
        }
        List<City> cities = new ArrayList<>(Arrays.asList(currentCity, neighbor));
        currentRoute.setCities(cities);
        return currentRoute;
    }

    /**
     * Adds routes from source city to neighbor current city
     *
     * @param neighborRout route from the current city to the neighbor city
     * @param currentRoute route from source city to current city
     * @return routes from source city to neighbor current city
     */
    private Route addRouts(Route neighborRout, Route currentRoute) {
        int cost = currentRoute.getCost() + neighborRout.getCost();
        Route route = new Route(cost, currentRoute.getSource(), neighborRout.getDestination());
        route.setEndRoute(neighborRout.isEndRoute());
        List<City> cities = new ArrayList<>(currentRoute.getCities());
        cities.add(route.getDestination());
        route.setCities(cities);
        return route;
    }

    /**
     * Delete oldRoutes ans add newRoutes
     *
     * @param oldRoutes routes from source city to current city
     * @param newRoutes routes from source city to neighbor current city
     * @param mainRoute main route that stores the minimum routes
     */
    private void editRoutes(List<Route> oldRoutes, List<Route> newRoutes, Route mainRoute) {
        if (newRoutes != null) {
            mainRoute.getRoutes().removeAll(oldRoutes);
            mainRoute.getRoutes().addAll(newRoutes);
        }
        checkRoutesEnd(mainRoute);
    }

    /**
     * Check all routes is end
     *
     * @param mainRoute main route that stores the minimum routes
     */
    private void checkRoutesEnd(Route mainRoute) {
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

    /**
     * Removes the complete routes which is more than the minimum route
     *
     * @param mainRoute main route that stores the minimum routes
     */
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

    /**
     * Find a minimal  cost route from all routes
     *
     * @param mainRoute main route that stores the minimum routes
     */
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
