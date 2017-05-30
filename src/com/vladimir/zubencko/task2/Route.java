package com.vladimir.zubencko.task2;

import java.util.List;

/**
 * Route from parameters: @{@link Route#endRoute}, @{@link Route#cost}, @{@link Route#source},@{@link Route#destination}, @{@link Route#cities}, @{@link Route#routes}
 *
 * @author Vladimir Zubencko
 * @version 1.0
 */
public class Route {

    /**
     * End route
     */
    private boolean endRoute;

    /**
     * Cost route
     */
    private int cost;

    /**
     * Source city
     */
    private City source;

    /**
     * Destination city
     */
    private City destination;

    /**
     * List route cities
     */
    private List<City> cities;

    /**
     * List routes
     */
    private List<Route> routes;

    /**
     * Create new object
     */
    public Route() {
    }

    /**
     * Create new object
     *
     * @param cities @{@link Route#cities}
     */
    public Route(List<City> cities) {
        this.cities = cities;
    }

    /**
     * Create new object
     *
     * @param cost        @{@link Route#cost}
     * @param source      @{@link Route#source}
     * @param destination @{@link Route#destination}
     */
    public Route(int cost, City source, City destination) {
        this.cost = cost;
        this.source = source;
        this.destination = destination;
    }

    public boolean isEndRoute() {
        return endRoute;
    }

    public void setEndRoute(boolean endRoute) {
        this.endRoute = endRoute;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public City getSource() {
        return source;
    }

    public void setSource(City source) {
        this.source = source;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
