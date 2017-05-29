package com.vladimir.zubencko.task2;

import java.util.ArrayList;
import java.util.List;

public class RouteService {

    private City sourceCity;
    private City destinationCity;
    private List<City> cities;      // list all cities
    private Route mainRoute;        // minRoute from destination city to source city

    public RouteService(City sourceCity, City destinationCity, List<City> cities) {
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.cities = cities;
    }

    // manager search min route
    Route calculateMinRoute() {
        mainRoute = new Route(); //create new object Route
        validateData();          //
        calculateFirsNeighbors();
        calculateRoute();
        return mainRoute;
    }

    // data validation
    private void validateData() {
        if (cities == null) {   // check the list of cities on  empty
            throw new IllegalArgumentException("Illegal data");     //generate Exception
        }
        if (sourceCity.getIndex() == destinationCity.getIndex()) {  //check source city  equals  destination city
            throw new IllegalArgumentException("Illegal data");
        }
        boolean isSource = false;                                   // indicator existence source city
        boolean isDestination = false;                              // indicator existence destination city
        for (City city : cities) { // loop at list city
            if (city.getIndex() == sourceCity.getIndex()) {         // check source city on existence
                isSource = true;                                    //source city exist
            }
            if (city.getIndex() == destinationCity.getIndex()) {    // check destination city on existence
                isDestination = true;                               //destination city exist
            }
        }
        if (!isSource && isDestination) {
            throw new IllegalArgumentException("Illegal data");
        }
    }

    // get first route
    private void calculateFirsNeighbors() {
        List<City> cities = new ArrayList<>();
        cities.add(this.cities.get(sourceCity.getIndex()));
        Route sourceRoute = new Route(cities);                          // create new object Route and set destination city
        Route route = calculateNeighborsRouts(sourceCity, sourceRoute); // get nearby from source city
        mainRoute.setRoutes(route.getRoutes());                         // add in min route firs routes
        checkMinRoute();
    }

    // calculate route from source city to destination city
    private void calculateRoute() {
        List<Route> newRoutes;
        List<Route> oldRoutes;
        while (!mainRoute.isEndRoute()) { // loop  until will not receive destination city
            List<Route> routes = mainRoute.getRoutes();
            oldRoutes = new ArrayList<>();
            newRoutes = new ArrayList<>();
            for (Route route : mainRoute.getRoutes()) { //loop from routes
                if (route != null) {
                    if (!route.isEndRoute()) { // if route not equals destination city
                        oldRoutes.add(route);
                        Route neighborsRouts = calculateNeighborsRouts(route.getDestination(), route);
                        try {
                            for (Route current : neighborsRouts.getRoutes()) { // loop from neighbor Routs
                                Route currentRoute = addRouts(current, route);
                                newRoutes.add(currentRoute);
                            }
                        } catch (NullPointerException e) { // route not connected with destination city
                        }
                    }
                }
            }
            editRoutes(routes, oldRoutes, newRoutes); // get proper route
            checkMinRoute(); // get min route from routes
        }

    }

    // get neighbors routs
    private Route calculateNeighborsRouts(City destinationCity, Route visitCity) { // current city  and visited city
        Route route = new Route();
        City city = cities.get(destinationCity.getIndex());         // get index current city
        route.setSource(city);                                      // add city from connection
        List<Route> routes = new ArrayList<>();
        List<City> neighbors = city.getCities();                    // get neighbor cities
        for (City neighbor : neighbors) {
            if (!isVisited(visitCity, neighbor)) {                  // check visited
                Route neighborRout = calculateRout(neighbor, city); //get route from current city
                routes.add(neighborRout);
                route.setRoutes(routes);                            // add current city route
            }
        }
        return route; // return route from current city
    }

    //check visited
    private boolean isVisited(Route passedCity, City neighbor) {
        boolean visit = false;                                       // indicator visit
        for (City city : passedCity.getCities()) {                   // loop for passed city
            if (city.getIndex() == neighbor.getIndex()) {
                visit = true;
                break;
            }
        }
        return visit;
    }

    //get route from current city
    private Route calculateRout(City neighbor, City city) {
        Route currentRoute = new Route(neighbor.getCoast(), city, neighbor); // current routs from destination city
        if (neighbor.getIndex() == destinationCity.getIndex()) {        //  check  current city equals destination city
            currentRoute.setEndRoute(true);                             //set indicator end route
        } else {
            currentRoute.setEndRoute(false);                            //set indicator end route
        }
        List<City> cities = new ArrayList<>();
        cities.add(city);
        cities.add(neighbor);
        currentRoute.setCities(cities);
        return currentRoute;
    }

    // add city to route
    private Route addRouts(Route current, Route route) {
        int cost = route.getCost() + current.getCost();
        Route currentRoute = new Route(cost, route.getSource(), current.getDestination());
        currentRoute.setEndRoute(current.isEndRoute());
        List<City> cities = new ArrayList<>(route.getCities());
        cities.add(currentRoute.getDestination());
        currentRoute.setCities(cities);
        return currentRoute;
    }

    // edit route
    private void editRoutes(List<Route> routes, List<Route> oldRoutes, List<Route> newRoutes) {
        Boolean endRoute = null;            // indicator end route
        if (newRoutes != null) {
            routes.removeAll(oldRoutes);    // delete route from previous city to current city
            routes.addAll(newRoutes);       // add new route from source city to current city
        }
        for (Route route : mainRoute.getRoutes()) {
            if (route.isEndRoute()) {       //check list route, all route have destination city
                endRoute = true;
            } else {
                endRoute = false;
                break;
            }
        }
        if (endRoute) {
            mainRoute.setEndRoute(true);    // all route have destination city
        }
    }

    private void checkMinRoute() {
        findEndMinRoute();                                                  //check  is min route end
        List<Route> routes = mainRoute.getRoutes();                     //get list routes
        Route minRoute = null;                                          // min route
        boolean isMinRoute = false;                                     // indicator min route
        for (int i = 0; i < routes.size(); i++) {                       //loop from routes
            Route currentRoute = routes.get(i);                         // get current route
            if (currentRoute.isEndRoute()) {                            // route have destination city
                if (!isMinRoute) {                                      // check min route
                    minRoute = routes.get(i);                           // set min route
                    isMinRoute = true;
                } else {
                    if (currentRoute.getCost() < minRoute.getCost()) {  // check current route < min route
                        routes.remove(minRoute);                        // remove min route
                        minRoute = currentRoute;                        // min route set current route
                        i--;                                            // iterator set previous
                    }
                    if (currentRoute.getCost() > minRoute.getCost()) {   // check current route > min route
                        routes.remove(routes.get(i));                   // delete current route
                        i--;                                            // iterator set previous
                    }
                }
            }
        }
    }

    private void findEndMinRoute() {
        boolean isMinRoute = false;
        Route minRoute = null;
        List<Route> removeRoutes = new ArrayList<>();        // unfinished route
        List<Route> routes = mainRoute.getRoutes();
        for (Route route : routes) {                        // loop from routes
            if (!isMinRoute) {                              // check min route
                minRoute = route;                           // set min route
                isMinRoute = true;
            }
            if (minRoute.getCost() > route.getCost()) {     // check cost min route > cost current route
                minRoute = route;
            }
        }
        if (minRoute.isEndRoute()) {                        // check route is end
            mainRoute.setEndRoute(true);                    // set min route
            for (Route route : routes) {                    // loop from routes
                if (!route.isEndRoute()) {                  // check route is end
                    removeRoutes.add(route);                // add unfinished routes
                }
            }
            routes.removeAll(removeRoutes);                 // delete unfinished routes
        }
    }
}
