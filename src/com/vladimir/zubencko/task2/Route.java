package com.vladimir.zubencko.task2;

import java.util.List;

public class Route {

    private boolean endRoute;

    private  int cost;

    private City source;

    private City destination;

    private List<City> cities;

    private List<Route> routes;

    public Route() {
    }

    public Route(List<City> cities) {
        this.cities = cities;
    }

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
